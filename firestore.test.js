const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "alice_csir_123";
const BOB_UID = "bob_csir_456";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

test("Unauthenticated user cannot read or write topicProgress or notes", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  const now = new Date(Date.now() - 1000);

  await assertFails(unauthDb.collection("users").doc(ALICE_UID).collection("topicProgress").get());
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).collection("notes").get());
  await assertFails(
    unauthDb.collection("users").doc(ALICE_UID).collection("topicProgress").doc("u1_enzymes").set({
      userId: ALICE_UID,
      unitNumber: 1,
      status: "MASTERED",
      bookmarked: true,
      createdAt: now,
      updatedAt: now,
    })
  );
  await assertFails(
    unauthDb.collection("users").doc(ALICE_UID).collection("notes").doc("note_1").set({
      userId: ALICE_UID,
      unitNumber: 1,
      title: "Km & Vmax",
      content: "Lineweaver-Burk x-intercept is -1/Km.",
      tag: "FORMULA",
      createdAt: now,
      updatedAt: now,
    })
  );
});

test("Owner can create, list, partially update, and delete topicProgress matching Kotlin model", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const created = new Date(Date.now() - 5000);
  const updated = new Date(Date.now() - 1000);

  const progressRef = aliceDb.collection("users").doc(ALICE_UID).collection("topicProgress").doc("u1_enzyme_kinetics");

  // Create without @DocumentId 'id'
  await assertSucceeds(
    progressRef.set({
      userId: ALICE_UID,
      unitNumber: 1,
      status: "REVISING",
      bookmarked: true,
      createdAt: created,
      updatedAt: created,
    })
  );

  // List subcollection
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("topicProgress").get()
  );

  // Partial update status & bookmarked preserving createdAt
  await assertSucceeds(
    progressRef.update({
      status: "MASTERED",
      bookmarked: false,
      updatedAt: updated,
    })
  );

  // Reject immutable createdAt / unitNumber change
  await assertFails(
    progressRef.update({
      unitNumber: 2,
      updatedAt: updated,
    })
  );
  await assertFails(
    progressRef.update({
      createdAt: updated,
      updatedAt: updated,
    })
  );

  // Reject invalid status enum or invalid unitNumber
  await assertFails(
    progressRef.update({
      status: "UNKNOWN_STATUS",
      updatedAt: updated,
    })
  );

  // Reject @DocumentId 'id' or ghost field on create
  await assertFails(
    aliceDb.collection("users").doc(ALICE_UID).collection("topicProgress").doc("u2_membranes").set({
      id: "u2_membranes",
      userId: ALICE_UID,
      unitNumber: 2,
      status: "NOT_STARTED",
      bookmarked: false,
      createdAt: created,
      updatedAt: created,
    })
  );

  await assertSucceeds(progressRef.delete());
});

test("Owner can create, update, list, and delete personal notes with optional topic fields omitted", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const created = new Date(Date.now() - 5000);
  const updated = new Date(Date.now() - 1000);

  const noteRef = aliceDb.collection("users").doc(ALICE_UID).collection("notes").doc("note_lac_operon");

  // Create without optional topicId/topicTitle
  await assertSucceeds(
    noteRef.set({
      userId: ALICE_UID,
      unitNumber: 3,
      title: "Lac Operon Merodiploid Trap",
      content: "Is is trans-dominant; Oc is cis-dominant.",
      tag: "PYQ_TRAP",
      createdAt: created,
      updatedAt: created,
    })
  );

  // Create with optional topicId & topicTitle
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("notes").doc("note_calvin").set({
      userId: ALICE_UID,
      unitNumber: 6,
      topicId: "u6_photosynthesis",
      topicTitle: "Photosynthesis & C3/C4/CAM",
      title: "RuBisCO Oxygenase",
      content: "High O2/CO2 ratio and high temp favor photorespiration in C3 plants.",
      tag: "REVISION",
      createdAt: created,
      updatedAt: created,
    })
  );

  // List query matching NoteRepository
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("notes").get()
  );

  // Partial update
  await assertSucceeds(
    noteRef.update({
      title: "Lac Operon Cis vs Trans",
      content: "Oc is strictly cis-dominant; I- is recessive; Is is trans-dominant.",
      tag: "MNEMONIC",
      updatedAt: updated,
    })
  );

  // Reject future timestamp
  await assertFails(
    noteRef.update({
      title: "Future spoof",
      updatedAt: new Date(Date.now() + 60000),
    })
  );

  // Reject empty title or invalid tag
  await assertFails(
    noteRef.update({
      title: "",
      updatedAt: updated,
    })
  );
  await assertFails(
    noteRef.update({
      tag: "INVALID_TAG",
      updatedAt: updated,
    })
  );

  await assertSucceeds(noteRef.delete());
});

test("Cross-user isolation blocks Bob from reading or modifying Alice data", async () => {
  const now = new Date(Date.now() - 5000);
  await testEnv.withSecurityRulesDisabled(async (context) => {
    const db = context.firestore();
    await db.collection("users").doc(ALICE_UID).collection("topicProgress").doc("u1_enzymes").set({
      userId: ALICE_UID,
      unitNumber: 1,
      status: "MASTERED",
      bookmarked: true,
      createdAt: now,
      updatedAt: now,
    });
    await db.collection("users").doc(ALICE_UID).collection("notes").doc("alice_note").set({
      userId: ALICE_UID,
      unitNumber: 1,
      title: "Alice Private Note",
      content: "Henderson-Hasselbalch pH = pKa + log([A-]/[HA])",
      tag: "FORMULA",
      createdAt: now,
      updatedAt: now,
    });
  });

  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("topicProgress").get());
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("topicProgress").doc("u1_enzymes").get());
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("topicProgress").doc("u1_enzymes").update({
    status: "NOT_STARTED",
    updatedAt: new Date(Date.now() - 1000),
  }));
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("notes").get());
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("notes").doc("alice_note").get());
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("notes").doc("alice_note").delete());
});
