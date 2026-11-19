package com.example.data

import com.example.model.ConceptSection
import com.example.model.CsirUnit
import com.example.model.StudyTable
import com.example.model.StudyTopic

val units5To8: List<CsirUnit> = listOf(
    CsirUnit(
        unitNumber = 5,
        title = "Developmental Biology",
        shortTitle = "Morphogenesis & Model Organisms",
        partFocus = "Part C Experimental Favorite",
        weightagePercent = 9,
        accentHex = 0xFF7B4B94,
        summary = "Potency & specification, fertilization, Drosophila A-P/D-V axis, amphibian organizer, C. elegans vulva, vertebrate limb patterning, and ABC floral model.",
        topics = listOf(
            StudyTopic(
                id = "u5_potency_fertilization_drosophila",
                unitNumber = 5,
                title = "Specification, Fertilization & Drosophila Axis",
                subtitle = "Autonomous vs conditional, acrosome/cortical reaction, Bicoid/Hox",
                highYield = true,
                estimatedMinutes = 15,
                overview = "Understand experimental transplantation outcomes and the sequential maternal-to-zygotic gene hierarchy in Drosophila.",
                sections = listOf(
                    ConceptSection(
                        heading = "Commitment & Specification Modes",
                        bullets = listOf(
                            "Autonomous specification (mosaic): Cytoplasmic determinants (e.g., tunicate yellow crescent); isolated blastomeres make only their normal fate.",
                            "Conditional specification (regulative): Cell-cell signaling/morphogen gradients (vertebrates); isolated blastomeres compensate and regulate.",
                            "Syncytial specification: Variable nuclear cleavage in shared cytoplasm before cellularization (Drosophila)."
                        )
                    ),
                    ConceptSection(
                        heading = "Drosophila Segmentation Cascade",
                        bullets = listOf(
                            "Maternal Effect Genes: Bicoid (anterior morphogen; activates Hunchback, represses Caudal translation) & Nanos (posterior; represses Hunchback translation). Dorsal protein nuclear gradient sets D-V axis (high nuclear Dorsal = ventral mesoderm).",
                            "Gap Genes (Kruppel, Knirps, Giant, Hunchback) → Pair-Rule Genes (Even-skipped, Fushi tarazu; 7 stripes) → Segment Polarity Genes (Engrailed, Wingless; 14 parasegments) → Homeotic (Hox) Selector Genes (Antennapedia & Bithorax complexes)."
                        )
                    )
                ),
                pyqPointers = listOf(
                    "Sea urchin fertilization: Fast block = Na+ influx depolarization; Slow block = Ca2+ wave → cortical granule exocytosis → hyaline/fertilization envelope.",
                    "Homeotic mutation: Loss-of-function in Ultrabithorax (Ubx) converts halteres (T3) into a second pair of wings (four-winged fly)."
                )
            ),
            StudyTopic(
                id = "u5_organizer_vulva_limb_abc",
                unitNumber = 5,
                title = "Organizer, C. elegans Vulva, Limb & ABC Flower Model",
                subtitle = "Spemann-Mangold, Anchor cell LIN-3/Notch, AER/ZPA, MADS-box",
                highYield = true,
                estimatedMinutes = 16,
                overview = "Classic inductive signaling networks tested via mutant phenotypes in CSIR Part C.",
                sections = listOf(
                    ConceptSection(
                        heading = "Amphibian Organizer & C. elegans Vulva",
                        bullets = listOf(
                            "Amphibian Nieuwkoop center (VegT + nuclear β-catenin) induces Spemann-Mangold organizer (dorsal blastopore lip), which secretes BMP antagonists (Noggin, Chordin, Follistatin) → neural induction by default!",
                            "C. elegans Vulva: Anchor cell secretes LIN-3 (EGF). Nearest P6.p receives highest LIN-3 → 1° fate (Ras/MAPK) and signals via LIN-12 (Notch) to P5.p & P7.p → 2° fate. P3.p/P4.p/P8.p → 3° hypodermal fate."
                        )
                    ),
                    ConceptSection(
                        heading = "Vertebrate Limb & Plant ABC Model",
                        bullets = listOf(
                            "Limb bud: Proximal-Distal axis driven by AER (FGF8/FGF4); Anterior-Posterior axis driven by ZPA (Sonic Hedgehog, Shh); Dorsal-Ventral by Wnt7a (dorsal) vs En1 (ventral).",
                            "ABC Floral Organ Model: Sepals (A), Petals (A+B), Stamens (B+C), Carpels (C). A and C are mutually antagonistic! Loss of C (agamous) produces Sepal-Petal-Petal-Sepal."
                        )
                    )
                ),
                comparisonTable = StudyTable(
                    title = "ABC Floral Mutant Phenotypes",
                    headers = listOf("Mutant", "Whorl 1", "Whorl 2", "Whorl 3", "Whorl 4"),
                    rows = listOf(
                        listOf("Wild Type", "Sepal (A)", "Petal (A+B)", "Stamen (B+C)", "Carpel (C)"),
                        listOf("Class A (apetala2)", "Carpel (C)", "Stamen (B+C)", "Stamen (B+C)", "Carpel (C)"),
                        listOf("Class B (ap3 / pi)", "Sepal (A)", "Sepal (A)", "Carpel (C)", "Carpel (C)"),
                        listOf("Class C (agamous)", "Sepal (A)", "Petal (A+B)", "Petal (A+B)", "Sepal (A)")
                    )
                ),
                pyqPointers = listOf(
                    "Transplanting ZPA to the anterior margin of a chick limb bud yields mirror-image digit duplication (4-3-2-2-3-4).",
                    "In Arabidopsis, Class E (SEPALLATA 1-4) proteins form tetrameric 'floral quartet' complexes with A/B/C proteins."
                )
            )
        )
    ),
    CsirUnit(
        unitNumber = 6,
        title = "System Physiology – Plant",
        shortTitle = "Plant Physiology & Hormones",
        partFocus = "Part B & C High-Yield",
        weightagePercent = 9,
        accentHex = 0xFF2E7D32,
        summary = "Light reactions & Z-scheme, C3/C4/CAM & photorespiration, nitrogenase, phytohormone signaling (Auxin/GA/CK/ABA/Ethylene), and phytochromes.",
        topics = listOf(
            StudyTopic(
                id = "u6_photosynthesis_nitrogen",
                unitNumber = 6,
                title = "Photosynthesis, C3/C4/CAM & Nitrogen Fixation",
                subtitle = "PSII/PSI Z-scheme, Calvin cycle, Photorespiration, Nif/Nod factors",
                highYield = true,
                estimatedMinutes = 15,
                overview = "Contrast ATP/NADPH stoichiometry across carbon fixation pathways and master nitrogenase biochemistry.",
                sections = listOf(
                    ConceptSection(
                        heading = "Light Reactions & Carbon Fixation",
                        bullets = listOf(
                            "Linear electron flow: H2O → PSII (P680, Mn4CaO5 OEC) → PQ → Cyt b6f (Q-cycle pumps H+) → Plastocyanin → PSI (P700) → Fd → FNR → NADPH.",
                            "Cyclic electron flow (PSI → Fd → PQ → Cyt b6f → PC) generates ATP ONLY (no NADPH, no O2).",
                            "Calvin Cycle (C3): Fixation (RuBisCO), Reduction, Regeneration. Costs 3 ATP + 2 NADPH per CO2 fixed.",
                            "Photorespiration (C2 cycle): RuBisCO oxygenase produces 2-phosphoglycolate → salvaged across Chloroplast → Peroxisome → Mitochondria (releases CO2 & NH3)."
                        )
                    ),
                    ConceptSection(
                        heading = "Biological Nitrogen Fixation",
                        bullets = listOf(
                            "Nitrogenase (MoFe + Fe protein): N2 + 8H+ + 8e- + 16 ATP → 2 NH3 + H2 + 16 ADP + 16 Pi.",
                            "Strictly O2-sensitive; protected in legume nodules by Leghemoglobin (O2 buffer) and thick cortex."
                        )
                    )
                ),
                comparisonTable = StudyTable(
                    title = "C3 vs C4 vs CAM Plants",
                    headers = listOf("Feature", "C3", "C4 (Kranz)", "CAM"),
                    rows = listOf(
                        listOf("Initial Enzyme", "RuBisCO", "PEP Carboxylase", "PEP Carboxylase (night)"),
                        listOf("First Product", "3-PGA (3C)", "Oxaloacetate (4C)", "Oxaloacetate → Malate"),
                        listOf("ATP / NADPH per CO2", "3 ATP, 2 NADPH", "5 ATP, 2 NADPH", "5.5–6.5 ATP, 2 NADPH"),
                        listOf("Photorespiration", "High", "Negligible", "Very Low")
                    )
                ),
                pyqPointers = listOf(
                    "DCMU (Diuron) blocks QB site of PSII; Paraquat accepts e- from PSI producing superoxide.",
                    "Phloem loading against concentration gradient uses Sucrose-H+ symporter (SUT1) energized by H+-ATPase."
                )
            ),
            StudyTopic(
                id = "u6_hormones_photomorphogenesis",
                unitNumber = 6,
                title = "Phytohormones & Photomorphogenesis",
                subtitle = "Ubiquitin-mediated derepression (Auxin/GA/JA), Cytokinin, ABA, Phytochrome",
                highYield = true,
                estimatedMinutes = 15,
                overview = "Notice the unifying paradigm in plant hormone signaling: Auxin, Gibberellin, and Jasmonate work via SCF E3 ligase degradation of transcriptional repressors!",
                sections = listOf(
                    ConceptSection(
                        heading = "Hormone Receptors & Mechanisms",
                        bullets = listOf(
                            "Auxin (IAA): Binds TIR1 (F-box of SCF) → ubiquitinates Aux/IAA repressors → frees ARF transcription factors. Polar transport via PIN efflux carriers.",
                            "Gibberellin (GA): Binds soluble GID1 → recruits SCF-SLY1 → degrades DELLA repressors → induces α-amylase in aleurone.",
                            "Cytokinin: Two-component His-kinase receptors (AHK → AHP → ARR). High CK/Auxin = shoot; low CK/Auxin = root.",
                            "Ethylene (gaseous): Inhibits ETR1/CTR1 (Raf-like) → stabilizes EIN2/EIN3 → Triple response (short swollen hypocotyl, apical hook).",
                            "ABA (stress/stomatal closure): Binds PYR/PYL/RCAR → inhibits PP2C phosphatase → activates SnRK2 kinase → opens SLAC1 anion channels."
                        )
                    ),
                    ConceptSection(
                        heading = "Photoreceptors & Flowering",
                        bullets = listOf(
                            "Phytochromes (Pr 660 nm inactive ↔ Pfr 730 nm active): Far-red reverses red-light responses (lettuce seed germination).",
                            "Photoperiodism: Short-day plants require uninterrupted dark period longer than critical night length; a night-break flash of Red light inhibits flowering (reversed by Far-Red)."
                        )
                    )
                ),
                pyqPointers = listOf(
                    "Brassinosteroids bind PM kinase BRI1 (like animal RTKs) rather than an intracellular SCF F-box receptor.",
                    "Florigen is FT protein synthesized in leaves and transported via phloem to shoot apical meristem."
                )
            )
        )
    ),
    CsirUnit(
        unitNumber = 7,
        title = "System Physiology – Animal",
        shortTitle = "Animal & Human Physiology",
        partFocus = "Part B & C High-Yield",
        weightagePercent = 9,
        accentHex = 0xFFB84A39,
        summary = "Cardiac cycle & ECG, O2-Hb Bohr/Haldane shifts, nephron countercurrent & RAAS, action potential (Nernst/Goldman), and endocrine axes.",
        topics = listOf(
            StudyTopic(
                id = "u7_cardio_respiratory_renal",
                unitNumber = 7,
                title = "Cardiovascular, Respiratory & Renal Physiology",
                subtitle = "ECG waves, Bohr effect, Nephron segments, Countercurrent & RAAS",
                highYield = true,
                estimatedMinutes = 16,
                overview = "Quantitative grasp of cardiac output, hemoglobin cooperativity, and nephron solute/water handling.",
                sections = listOf(
                    ConceptSection(
                        heading = "Cardiac Cycle & ECG",
                        bullets = listOf(
                            "ECG: P wave (atrial depolarization), QRS complex (ventricular depolarization; masks atrial repolarization), T wave (ventricular repolarization).",
                            "S1 ('lub') = AV valve closure at isovolumetric contraction; S2 ('dub') = semilunar valve closure at isovolumetric relaxation.",
                            "Cardiac Output (CO) = Stroke Volume (EDV - ESV) × Heart Rate."
                        )
                    ),
                    ConceptSection(
                        heading = "Respiration & Acid-Base Balance",
                        bullets = listOf(
                            "Bohr effect: ↑CO2, ↓pH (↑H+), ↑Temp, ↑2,3-BPG shift O2-Hb curve RIGHT (lower affinity → O2 unloading in tissues). Fetal Hb (α2γ2) binds 2,3-BPG poorly → shifted LEFT.",
                            "Haldane effect: Deoxygenation of Hb increases its ability to carry CO2 (as carbamino-Hb and H+ buffering)."
                        )
                    ),
                    ConceptSection(
                        heading = "Nephron & RAAS",
                        bullets = listOf(
                            "PCT: Reabsorbs ~65% Na+/H2O, 100% glucose/amino acids (SGLT2). Descending Loop of Henle: permeable to H2O only; Ascending thick limb: impermeable to H2O, active NKCC2 transport.",
                            "ADH (Vasopressin): Inserts Aquaporin-2 into collecting duct principal cells via V2R–cAMP. Aldosterone: Induces ENaC & Na+/K+-ATPase in late DCT/CD."
                        )
                    )
                ),
                keyFormulas = listOf(
                    "GFR = (U_inulin × V) / P_inulin  |  Renal Plasma Flow = C_PAH",
                    "Vital Capacity = TV + IRV + ERV  |  TLC = VC + RV",
                    "Mean Arterial Pressure (MAP) = Diastolic + 1/3(Systolic - Diastolic)"
                ),
                pyqPointers = listOf(
                    "Chloride shift (Hamburger phenomenon): In RBCs, HCO3- exits via Band 3 antiporter in exchange for Cl-.",
                    "Macula densa senses low NaCl in DCT → juxtaglomerular cells release Renin → Angiotensinogen → Ang I → Ang II (via ACE) → vasoconstriction + Aldosterone."
                )
            ),
            StudyTopic(
                id = "u7_neuro_endocrinology",
                unitNumber = 7,
                title = "Neurophysiology & Endocrinology",
                subtitle = "Action potential, Nernst equation, Pituitary/Thyroid/Adrenal/Pancreas",
                highYield = true,
                estimatedMinutes = 14,
                overview = "Membrane biophysics of excitable cells and feedback loops of major endocrine glands.",
                sections = listOf(
                    ConceptSection(
                        heading = "Action Potential & Synaptic Transmission",
                        bullets = listOf(
                            "Resting potential (~ -70 mV) dominated by K+ leak channels. Depolarization opens voltage-gated Na+ channels (+35 mV); inactivation + delayed K+ efflux drives repolarization & hyperpolarization.",
                            "Absolute refractory period: Voltage-gated Na+ inactivation gates (h-gate) are closed; no stimulus can fire an AP.",
                            "Synapse: Action potential opens voltage-gated Ca2+ channels → Synaptotagmin triggers SNARE-mediated vesicle fusion."
                        )
                    ),
                    ConceptSection(
                        heading = "Endocrine Axes & Hormone Signaling",
                        bullets = listOf(
                            "Anterior Pituitary (tropic): GH, TSH, ACTH, FSH, LH, Prolactin (inhibited by hypothalamic Dopamine!). Posterior Pituitary (releases hypothalamic Oxytocin & ADH).",
                            "Insulin (RTK) promotes GLUT4 translocation in muscle/adipose and glycogenesis; Glucagon (GPCR–cAMP) activates glycogenolysis & gluconeogenesis.",
                            "Steroid & Thyroid hormones (T3/T4) bind nuclear/intracellular receptors acting directly as transcription factors."
                        )
                    )
                ),
                keyFormulas = listOf(
                    "Nernst Equation (at 37°C): E_ion = (61.5 / z) · log10([Ion]_out / [Ion]_in) mV",
                    "Goldman-Hodgkin-Katz considers relative permeabilities PK : PNa : PCl"
                ),
                pyqPointers = listOf(
                    "Tetrodotoxin (TTX) blocks voltage-gated Na+ channels; Tetraethylammonium (TEA) blocks voltage-gated K+ channels.",
                    "LH surge triggered by high sustained estrogen (positive feedback) induces ovulation; Corpus luteum secretes Progesterone."
                )
            )
        )
    ),
    CsirUnit(
        unitNumber = 8,
        title = "Inheritance Biology",
        shortTitle = "Genetics, Mapping & Pedigrees",
        partFocus = "Part B & C Numericals",
        weightagePercent = 10,
        accentHex = 0xFF5D6D2E,
        summary = "Mendelian extensions & epistasis, 3-point gene mapping & interference, microbial genetics (Hfr/transduction), pedigree analysis, and chromosomal aberrations.",
        topics = listOf(
            StudyTopic(
                id = "u8_mendelian_epistasis_pedigree",
                unitNumber = 8,
                title = "Epistasis Ratios, Pedigree Analysis & Polygenic Traits",
                subtitle = "Modified dihybrid ratios, autosomal/X-linked/mitochondrial rules",
                highYield = true,
                estimatedMinutes = 14,
                overview = "Rapidly recognize modified F2 ratios and solve multi-generation pedigree charts with zero guesswork.",
                sections = listOf(
                    ConceptSection(
                        heading = "Pedigree Decision Rules",
                        bullets = listOf(
                            "Autosomal Recessive: Skips generations; unaffected parents can have affected child; equal M/F; consanguinity common.",
                            "Autosomal Dominant: Every affected child has at least one affected parent; no generation skipped (unless incomplete penetrance).",
                            "X-Linked Recessive: Males predominantly affected; affected father NEVER transmits to sons (passes to all carrier daughters).",
                            "X-Linked Dominant: Affected father transmits to 100% of daughters and 0% of sons.",
                            "Mitochondrial (cytoplasmic): Exclusively maternal inheritance (affected mother passes to ALL children; affected father passes to NONE)."
                        )
                    )
                ),
                comparisonTable = StudyTable(
                    title = "Classic Epistasis Modified F2 Ratios",
                    headers = listOf("Interaction Type", "F2 Ratio", "Example"),
                    rows = listOf(
                        listOf("Recessive Epistasis", "9 : 3 : 4", "Labrador coat color; Bombay blood group"),
                        listOf("Dominant Epistasis", "12 : 3 : 1", "Summer squash fruit color"),
                        listOf("Duplicate Recessive (Complementary)", "9 : 7", "Sweet pea flower color"),
                        listOf("Duplicate Dominant", "15 : 1", "Shepherd's purse seed capsule"),
                        listOf("Dominant & Recessive Interaction", "13 : 3", "Chicken plumage color")
                    )
                ),
                keyFormulas = listOf(
                    "Polygenic additive loci (n genes): Number of phenotypic classes = 2n + 1; extreme parental fraction = (1/4)^n",
                    "Broad-sense heritability H² = VG / VP  |  Narrow-sense h² = VA / VP"
                ),
                pyqPointers = listOf(
                    "Complementation test determines if two recessive mutants with similar phenotype are allelic (no complementation = mutant F1) or in different genes (WT F1).",
                    "Chi-square degrees of freedom df = (number of phenotypic classes) - 1."
                )
            ),
            StudyTopic(
                id = "u8_mapping_microbial_aberrations",
                unitNumber = 8,
                title = "Gene Mapping, Microbial Genetics & Aberrations",
                subtitle = "3-point testcross, Coefficient of Coincidence, Hfr, Inversions & Translocations",
                highYield = true,
                estimatedMinutes = 16,
                overview = "Step-by-step algorithm for three-point linkage maps, interrupted mating, and meiotic chromosome pairing.",
                sections = listOf(
                    ConceptSection(
                        heading = "3-Point Testcross & Interference Algorithm",
                        bullets = listOf(
                            "Step 1: Identify the two largest progeny classes = Parental (non-recombinant).",
                            "Step 2: Identify the two smallest classes = Double Crossovers (DCO).",
                            "Step 3: Compare Parental vs DCO to find the gene in the MIDDLE (the one that flipped relative to the flanking two).",
                            "Step 4: Recombination frequency (RF) between A–B = (SCO_AB + DCO) / Total × 100 cM (1% RF = 1 map unit / cM)."
                        )
                    ),
                    ConceptSection(
                        heading = "Microbial Genetics & Chromosome Rearrangements",
                        bullets = listOf(
                            "Conjugation: F+ × F- transfers F plasmid; Hfr × F- transfers chromosomal markers linearly (interrupted mating maps in minutes).",
                            "Cotransduction distance (Wu formula): d = L × (1 - ∛f), where f is cotransduction frequency.",
                            "Paracentric inversion (excludes centromere) → crossover produces acentric fragment + dicentric bridge. Pericentric inversion (includes centromere) → duplications/deletions.",
                            "Reciprocal translocation heterozygote forms a quadrivalent cross at pachytene; alternate segregation gives viable balanced gametes."
                        )
                    )
                ),
                keyFormulas = listOf(
                    "Expected DCO = RF(A-B) × RF(B-C) × Total",
                    "Coefficient of Coincidence (CoC) = Observed DCO / Expected DCO",
                    "Interference (I) = 1 - CoC"
                ),
                pyqPointers = listOf(
                    "Maximum recombination frequency between any two linked genes in a single cross is 50% (same as independent assortment).",
                    "2n - 1 = Monosomy (Turner 45,XO); 2n + 1 = Trisomy (Down Trisomy 21, Klinefelter 47,XXY)."
                )
            )
        )
    )
)
