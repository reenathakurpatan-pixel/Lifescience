package com.example.data

import com.example.model.ConceptSection
import com.example.model.CsirUnit
import com.example.model.StudyTable
import com.example.model.StudyTopic

val units1To4: List<CsirUnit> = listOf(
    CsirUnit(
        unitNumber = 1,
        title = "Molecules and Their Interaction Relevant to Biology",
        shortTitle = "Biochemistry & Biomolecules",
        partFocus = "Part B & C High-Yield",
        weightagePercent = 10,
        accentHex = 0xFF0D6E5B,
        summary = "Chemical bonds, pH/buffers, bioenergetics, enzyme kinetics, protein folding, Ramachandran plots, carbohydrates, lipids, and nucleic acid thermodynamics.",
        topics = listOf(
            StudyTopic(
                id = "u1_bonds_ph_thermodynamics",
                unitNumber = 1,
                title = "Chemical Bonds, pH, Buffers & Bioenergetics",
                subtitle = "Non-covalent forces, Henderson-Hasselbalch, Gibbs free energy",
                highYield = true,
                estimatedMinutes = 14,
                overview = "Weak non-covalent interactions stabilize macromolecular 3D folds while permitting dynamic conformational changes. Bioenergetics governs metabolic coupling via ATP hydrolysis.",
                sections = listOf(
                    ConceptSection(
                        heading = "Non-Covalent Interactions in Water",
                        bullets = listOf(
                            "Hydrogen bonds (12–30 kJ/mol): Directional; strongest when donor-H-acceptor angle is 180°. Crucial for α-helices, β-sheets, and DNA base pairing.",
                            "Ionic/Electrostatic bonds (20 kJ/mol): Follow Coulomb's law (F = q1q2 / εr²); shielded in water due to high dielectric constant (ε ≈ 80).",
                            "Hydrophobic effect: Entropy-driven (ΔS > 0 of bulk water) clustering of non-polar side chains in the protein core; major driver of protein folding.",
                            "van der Waals forces (0.4–4 kJ/mol): Short-range London dispersion + steric repulsion; optimal at van der Waals contact distance."
                        )
                    ),
                    ConceptSection(
                        heading = "pH, pKa, Isoelectric Point (pI) & Buffers",
                        bullets = listOf(
                            "Maximum buffering capacity occurs at pH = pKa (±1 pH unit window).",
                            "Neutral amino acids: pI = (pKa1 + pKa2) / 2. Acidic amino acids (Asp, Glu): average the two acidic pKa values. Basic amino acids (Lys, Arg, His): average the two basic pKa values.",
                            "Histidine (side chain pKa ≈ 6.0) is the premier physiological protein buffer near pH 7.0."
                        )
                    ),
                    ConceptSection(
                        heading = "Thermodynamics & Coupled Reactions",
                        bullets = listOf(
                            "Spontaneous reaction requires ΔG < 0 (exergonic). At equilibrium, ΔG = 0 and ΔG°' = -RT ln(Keq').",
                            "ATP hydrolysis (ΔG°' = -30.5 kJ/mol) drives unfavorable reactions (e.g., Glucose → Glucose-6-P, ΔG°' = +13.8 kJ/mol) via a shared phosphorylated intermediate."
                        )
                    )
                ),
                comparisonTable = StudyTable(
                    title = "Amino Acid Side Chain Classifications",
                    headers = listOf("Class", "Residues", "Key CSIR Property"),
                    rows = listOf(
                        listOf("Non-polar Aliphatic", "G, A, V, L, I, P, M", "Proline disrupts α-helix; Glycine gives flexibility"),
                        listOf("Aromatic", "F, Y, W", "Trp (W) dominates 280 nm UV absorbance (ε ≈ 5500)"),
                        listOf("Polar Uncharged", "S, T, C, N, Q", "Ser/Thr/Tyr are phosphorylation sites; Cys forms S-S"),
                        listOf("Positively Charged", "K, R, H", "Histones rich in Lys/Arg; His pKa ≈ 6.0"),
                        listOf("Negatively Charged", "D, E", "Asp/Glu carry -1 charge at pH 7.4")
                    )
                ),
                keyFormulas = listOf(
                    "pH = pKa + log([A-] / [HA])  [Henderson-Hasselbalch]",
                    "ΔG = ΔH - TΔS  |  ΔG = ΔG°' + RT ln([Products]/[Reactants])",
                    "ΔG°' = -n · F · ΔE°'  (F = 96.485 kJ/V·mol)",
                    "pI = (pKa_low + pKa_high) / 2"
                ),
                pyqPointers = listOf(
                    "Adding a catalyst/enzyme lowers activation energy (ΔG‡) ONLY — it never alters ΔG°' or Keq.",
                    "At pH > pI, a peptide carries net negative charge and migrates toward the anode (+) in electrophoresis.",
                    "Trp fluorescence (excitation 280 nm, emission ~340 nm) shifts to shorter wavelength (blue shift) when buried in a hydrophobic core."
                )
            ),
            StudyTopic(
                id = "u1_enzyme_kinetics",
                unitNumber = 1,
                title = "Enzyme Kinetics, Inhibition & Allostery",
                subtitle = "Michaelis-Menten, Lineweaver-Burk, kcat/Km & inhibitors",
                highYield = true,
                estimatedMinutes = 16,
                overview = "One of the most frequently tested Part B & C topics. Master graphical shifts in Lineweaver-Burk plots and numerical calculations of Vmax, Km, and catalytic efficiency.",
                sections = listOf(
                    ConceptSection(
                        heading = "Michaelis-Menten Assumptions & Constants",
                        bullets = listOf(
                            "Steady-state assumption: d[ES]/dt = 0; holds when [S] >> [E] and initial velocity v0 is measured.",
                            "Km = (k-1 + k2) / k1. Lower Km indicates higher apparent affinity only when k2 << k-1.",
                            "kcat (turnover number) = Vmax / [Et]. Catalytic efficiency = kcat / Km (diffusion limit ≈ 10^8–10^9 M⁻¹s⁻¹)."
                        )
                    ),
                    ConceptSection(
                        heading = "Allosteric Enzymes (MWC vs KNF)",
                        bullets = listOf(
                            "Exhibit sigmoidal v0 vs [S] curves (Hill coefficient nH > 1 indicates positive cooperativity).",
                            "Homotropic effectors (substrate itself) vs Heterotropic effectors (e.g., ATP activates, CTP inhibits ATCase)."
                        )
                    )
                ),
                comparisonTable = StudyTable(
                    title = "Reversible Enzyme Inhibition Summary",
                    headers = listOf("Type", "Binding Site", "Apparent Vmax", "Apparent Km", "Lineweaver-Burk Pattern"),
                    rows = listOf(
                        listOf("Competitive", "Free E (active site)", "Unchanged", "Increases (αKm)", "Lines intersect on Y-axis"),
                        listOf("Uncompetitive", "ES complex only", "Decreases (Vmax/α')", "Decreases (Km/α')", "Parallel lines (same slope)"),
                        listOf("Pure Non-competitive", "E and ES equally", "Decreases (Vmax/α')", "Unchanged", "Lines intersect on X-axis"),
                        listOf("Mixed", "E and ES unequally", "Decreases", "Increases or Decreases", "Intersect in 2nd or 3rd quadrant")
                    )
                ),
                keyFormulas = listOf(
                    "v0 = (Vmax · [S]) / (Km + [S])",
                    "1/v0 = (Km / Vmax)(1/[S]) + 1/Vmax   [Slope = Km/Vmax, Y-int = 1/Vmax, X-int = -1/Km]",
                    "Competitive factor α = 1 + [I]/Ki  |  Uncompetitive α' = 1 + [I]/Ki'",
                    "Hill Equation: log(Y / (1 - Y)) = nH·log[L] - log Kd"
                ),
                pyqPointers = listOf(
                    "Competitive inhibition can be overcome at saturating [S]; uncompetitive inhibition CANNOT.",
                    "Irreversible suicide inhibitors (e.g., DIPF on Ser proteases) reduce active [Et] → lower Vmax without changing Km, mimicking non-competitive kinetics.",
                    "Chymotrypsin catalytic triad: Asp102 – His57 – Ser195 (covalent + general acid-base catalysis)."
                )
            ),
            StudyTopic(
                id = "u1_protein_nucleic_structure",
                unitNumber = 1,
                title = "Protein Folding, Ramachandran Plot & Nucleic Acids",
                subtitle = "Phi/Psi angles, DNA A/B/Z forms, Tm & Cot curves",
                highYield = true,
                estimatedMinutes = 14,
                overview = "Understand steric constraints on polypeptide dihedral angles and physical chemistry of DNA denaturation and renaturation kinetics.",
                sections = listOf(
                    ConceptSection(
                        heading = "Ramachandran Plot & Secondary Structures",
                        bullets = listOf(
                            "Peptide bond (C-N) has ~40% partial double-bond character (planar, ω = 180° trans; Proline can adopt cis ω = 0°).",
                            "Allowed regions of φ (N-Cα) and ψ (Cα-C): Right-handed α-helix (-57°, -47°), Antiparallel β-sheet (-139°, +135°), Parallel β-sheet (-119°, +113°), Left-handed α-helix (+57°, +47°).",
                            "Glycine occupies the largest allowed area (R = H); Proline has the most restricted φ (≈ -60°)."
                        )
                    ),
                    ConceptSection(
                        heading = "DNA Forms (A, B, Z) & Melting Temperature (Tm)",
                        bullets = listOf(
                            "B-DNA: Right-handed, 10.5 bp/turn, 3.4 Å rise, C2'-endo sugar pucker, anti glycosidic bond.",
                            "A-DNA: Right-handed (dehydrated DNA & dsRNA/RNA-DNA hybrids), 11 bp/turn, C3'-endo pucker.",
                            "Z-DNA: Left-handed zig-zag, 12 bp/turn, alternating purine-pyrimidine (GC repeats), purines in syn and pyrimidines in anti.",
                            "Hyperchromic effect: ssDNA absorbs ~40% more at 260 nm than dsDNA due to unstacking of bases."
                        )
                    )
                ),
                keyFormulas = listOf(
                    "Tm increases with %GC content and higher monovalent salt [Na+]",
                    "Cot½ ∝ Genome Complexity (unique non-repetitive DNA size)",
                    "Linking Number: Lk = Tw + Wr  (Topological invariant unless strand is broken)"
                ),
                pyqPointers = listOf(
                    "In a Ramachandran plot, Quadrant II (top-left, -φ/+ψ) holds β-sheets and collagen helix; Quadrant III (bottom-left, -φ/-ψ) holds right-handed α-helix.",
                    "Circular dichroism (CD): α-helix shows double minima at 208 nm and 222 nm; β-sheet shows a single minimum near 218 nm."
                )
            )
        )
    ),
    CsirUnit(
        unitNumber = 2,
        title = "Cellular Organization",
        shortTitle = "Membranes, Organelles & Cell Cycle",
        partFocus = "Part B & C Core",
        weightagePercent = 10,
        accentHex = 0xFF1E5F74,
        summary = "Membrane dynamics, pumps/channels, protein sorting (ER/Golgi/lysosome), mitochondria/chloroplast bioenergetics, cytoskeleton, and CDK/cyclin cell cycle control.",
        topics = listOf(
            StudyTopic(
                id = "u2_membrane_transport",
                unitNumber = 2,
                title = "Membrane Structure, Lipid Rafts & Transport",
                subtitle = "Fluid mosaic model, FRAP, P-type/V-type/ABC transporters",
                highYield = true,
                estimatedMinutes = 14,
                overview = "Asymmetric lipid bilayers regulate fluidity via cholesterol and unsaturation, while specialized pumps maintain electrochemical gradients.",
                sections = listOf(
                    ConceptSection(
                        heading = "Lipid Asymmetry & Fluidity",
                        bullets = listOf(
                            "Outer leaflet: Phosphatidylcholine (PC), Sphingomyelin (SM), Glycolipids. Inner leaflet: Phosphatidylethanolamine (PE), Phosphatidylserine (PS), Phosphatidylinositol (PI).",
                            "PS exposure on the outer leaflet (by scramblase) is an early 'eat-me' signal of apoptosis.",
                            "Cholesterol acts as a bidirectional fluidity buffer: increases fluidity below Tm (prevents tight packing) and decreases fluidity above Tm.",
                            "Lipid rafts are detergent-resistant microdomains enriched in cholesterol, sphingolipids, and GPI-anchored proteins."
                        )
                    ),
                    ConceptSection(
                        heading = "Active Transport Classes",
                        bullets = listOf(
                            "P-type ATPases (phosphorylated Asp intermediate): Na+/K+ ATPase (3 Na+ out, 2 K+ in; inhibited by ouabain), SERCA Ca2+ pump.",
                            "V-type ATPases: Acidify lysosomes, endosomes, plant vacuoles (no phosphoenzyme intermediate).",
                            "F-type ATPases: Synthases in mitochondria, chloroplasts, bacterial plasma membrane.",
                            "ABC Transporters (MDR1 / P-glycoprotein): Dimeric ATP-binding cassette efflux pumps causing multidrug resistance."
                        )
                    )
                ),
                keyFormulas = listOf(
                    "ΔG_transport = RT ln(C_in / C_out) + z · F · ΔΨ",
                    "FRAP Diffusion Coefficient: D = ω² / (4 · t_½)"
                ),
                pyqPointers = listOf(
                    "Flip-flop (transverse diffusion) is slowest and requires ATP-dependent flippase (to inner) or floppase (to outer), whereas lateral diffusion is rapid (~1 μm²/s).",
                    "Valinomycin is a mobile K+ carrier ionophore; Gramicidin A is a channel-forming ionophore."
                )
            ),
            StudyTopic(
                id = "u2_protein_sorting_cytoskeleton",
                unitNumber = 2,
                title = "Protein Sorting, Vesicular Traffic & Cytoskeleton",
                subtitle = "SRP, KDEL, Mannose-6-P, COPII/COPI/Clathrin & motor proteins",
                highYield = true,
                estimatedMinutes = 15,
                overview = "Post-translational vs co-translational translocation and directional vesicle coat/motor systems.",
                sections = listOf(
                    ConceptSection(
                        heading = "Organellar Targeting Signals",
                        bullets = listOf(
                            "ER signal peptide (N-terminal hydrophobic): Recognized by SRP, pauses translation, docks at SRP receptor → Sec61 translocon.",
                            "ER retention signal: C-terminal KDEL (soluble) or KKXX (membrane) retrieved from Golgi via COPI vesicles.",
                            "Lysosomal hydrolases: Tagged with Mannose-6-Phosphate (M6P) in cis-Golgi; M6P receptor delivers from trans-Golgi.",
                            "Mitochondrial matrix: N-terminal amphipathic helix (+ charged) → TOM40 → TIM23 (requires ΔΨ and matrix mtHsp70).",
                            "Peroxisomes: C-terminal PTS1 (SKL) recognized by Pex5; imported in folded state."
                        )
                    ),
                    ConceptSection(
                        heading = "Cytoskeletal Filaments & Motors",
                        bullets = listOf(
                            "Microtubules (25 nm, α/β-tubulin GTP cap): Kinesins move (+)-end directed (except Kinesin-14); Dyneins move (-)-end directed.",
                            "Microfilaments (7 nm, F-actin ATP cap): Myosins move (+)-end directed (except Myosin VI).",
                            "Intermediate filaments (10 nm, non-polar): Keratin, Vimentin, Lamin (nuclear); NO associated motor proteins."
                        )
                    )
                ),
                comparisonTable = StudyTable(
                    title = "Vesicle Coats & GTPases",
                    headers = listOf("Coat", "Route", "GTPase", "Drug / Inhibitor Note"),
                    rows = listOf(
                        listOf("COPII", "ER → cis-Golgi (anterograde)", "Sar1-GTP", "Sec23/24 (cargo), Sec13/31 (cage)"),
                        listOf("COPI", "Golgi → ER (retrograde)", "ARF1-GTP", "Inhibited by Brefeldin A"),
                        listOf("Clathrin", "TGN/PM → Endosome", "ARF1 / Dynamin", "Dynamin-GTP pinches off vesicle")
                    )
                ),
                pyqPointers = listOf(
                    "Tunicamycin blocks N-linked glycosylation (dolichol pyrophosphate pathway) in ER.",
                    "Colchicine & Nocodazole depolymerize microtubules; Taxol (Paclitaxel) stabilizes microtubules; Cytochalasin D caps F-actin (+) end."
                )
            ),
            StudyTopic(
                id = "u2_cell_cycle_checkpoints",
                unitNumber = 2,
                title = "Cell Cycle Regulation, Checkpoints & Mitosis",
                subtitle = "Cyclin-CDK pairs, Rb-E2F, APC/C, cohesin & separase",
                highYield = true,
                estimatedMinutes = 14,
                overview = "Unidirectional progression through G1, S, G2, and M is enforced by ordered cyclin synthesis, CDK phosphorylation, and ubiquitin-mediated proteolysis.",
                sections = listOf(
                    ConceptSection(
                        heading = "Cyclin–CDK Complexes & Activation",
                        bullets = listOf(
                            "G1 phase: Cyclin D–CDK4/6 phosphorylates Rb → releases E2F transcription factor to induce Cyclin E.",
                            "G1/S transition: Cyclin E–CDK2 triggers origin firing; S phase: Cyclin A–CDK2 prevents re-replication.",
                            "G2/M (MPF): Cyclin B–CDK1. Inhibited by Wee1 (Tyr15-P) and activated by Cdc25 phosphatase (removes inhibitory P) + CAK (Thr161-P)."
                        )
                    ),
                    ConceptSection(
                        heading = "Checkpoints & Anaphase Entry",
                        bullets = listOf(
                            "DNA Damage Checkpoint: ATM/ATR → Chk1/Chk2 → stabilizes p53 → induces p21 (CIP/KIP CDK inhibitor) → G1 arrest.",
                            "Spindle Assembly Checkpoint (SAC): Unattached kinetochores activate Mad2/BubR1, inhibiting Cdc20-APC/C.",
                            "Anaphase onset: APC/C-Cdc20 ubiquitinates Securin → activates Separase → cleaves Cohesin rings; APC/C also degrades Cyclin B to allow mitotic exit."
                        )
                    )
                ),
                pyqPointers = listOf(
                    "SCF ubiquitin ligase acts at G1/S (degrades p27, Sic1); APC/C acts at Metaphase→Anaphase and G1.",
                    "INK4 inhibitors (p16) specifically inhibit CDK4/6; CIP/KIP (p21, p27, p57) inhibit all Cyclin-CDK complexes."
                )
            )
        )
    ),
    CsirUnit(
        unitNumber = 3,
        title = "Fundamental Processes",
        shortTitle = "Molecular Biology & Operons",
        partFocus = "Highest Weightage (Part C)",
        weightagePercent = 12,
        accentHex = 0xFF2D4F8E,
        summary = "DNA replication, damage & repair, transcription machinery, RNA processing/splicing, translation, Lac/Trp/Lambda regulation, and chromatin remodeling.",
        topics = listOf(
            StudyTopic(
                id = "u3_dna_replication_repair",
                unitNumber = 3,
                title = "DNA Replication & DNA Repair Pathways",
                subtitle = "Prokaryotic vs Eukaryotic polymerases, licensing, BER/NER/MMR/HR",
                highYield = true,
                estimatedMinutes = 16,
                overview = "High-fidelity replication relies on origin licensing, processive polymerases, and multi-layered DNA repair mechanisms.",
                sections = listOf(
                    ConceptSection(
                        heading = "Replication Machinery",
                        bullets = listOf(
                            "E. coli: DnaA binds 9-mer oriC → melts AT-rich 13-mer → DnaB (5'→3' helicase) loaded by DnaC → DnaG primase → Pol III core + β-sliding clamp (processivity) + γ-clamp loader; Pol I removes RNA primers (5'→3' exo).",
                            "Eukaryotes: ORC + Cdc6 + Cdt1 load MCM2-7 helicase ONLY in G1 (licensing). High CDK in S phase phosphorylates Cdc6/Cdt1/Geminin to prevent re-replication.",
                            "Eukaryotic Polymerases: Pol α (primase + short DNA), Pol δ (lagging strand + PCNA), Pol ε (leading strand), Pol γ (mitochondrial)."
                        )
                    ),
                    ConceptSection(
                        heading = "DNA Repair Mechanisms",
                        bullets = listOf(
                            "Mismatch Repair (MMR): MutS recognizes mismatch, MutL recruits MutH (endonuclease cleaves unmethylated newly synthesized strand at GATC in E. coli; defects → Lynch syndrome/HNPCC).",
                            "Base Excision Repair (BER): DNA glycosylase removes damaged base (e.g., uracil from cytosine deamination) → AP endonuclease → Pol β → ligase.",
                            "Nucleotide Excision Repair (NER): Bulky lesions/pyrimidine dimers; UvrABC in E. coli; XPA–XPG & TFIIH in humans (defects → Xeroderma pigmentosum).",
                            "DSB Repair: NHEJ (Ku70/80, DNA-PKcs, Ligase IV — error-prone, G1) vs Homologous Recombination (MRN, BRCA1/2, Rad51 — error-free, S/G2)."
                        )
                    )
                ),
                pyqPointers = listOf(
                    "Telomerase is a ribonucleoprotein reverse transcriptase (TERT + TERC RNA template) that extends the 3' G-rich overhang.",
                    "Aphidicolin inhibits eukaryotic Pol α, δ, ε; Dideoxynucleotides (ddNTPs) lack 3'-OH and terminate chain elongation."
                )
            ),
            StudyTopic(
                id = "u3_transcription_splicing_translation",
                unitNumber = 3,
                title = "Transcription, RNA Processing & Translation",
                subtitle = "RNA Pol I/II/III, spliceosome chemistry, wobble & elongation",
                highYield = true,
                estimatedMinutes = 16,
                overview = "Coordinate control of promoter clearance, co-transcriptional 5'-capping/splicing/polyadenylation, and GTP-driven ribosomal translation.",
                sections = listOf(
                    ConceptSection(
                        heading = "Transcription & CTD Code",
                        bullets = listOf(
                            "E. coli RNA Pol (α2ββ'ω + σ): σ70 recognizes -35 (TTGACA) and -10 Pribnow box (TATAAT). Rifampicin blocks initiation; Actinomycin D intercalates DNA.",
                            "Eukaryotic RNA Pols: Pol I (rRNA 18S, 5.8S, 28S; α-amanitin resistant), Pol II (mRNA, snRNA, miRNA; highly α-amanitin sensitive), Pol III (tRNA, 5S rRNA; intermediate sensitivity).",
                            "RNA Pol II CTD (YSPTSPS repeats): Ser5-P by TFIIH (recruits capping enzyme) → Ser2-P by P-TEFb (recruits splicing & 3'-cleavage/polyA factors)."
                        )
                    ),
                    ConceptSection(
                        heading = "Splicing & Translation Mechanics",
                        bullets = listOf(
                            "Spliceosome (U1, U2, U4, U5, U6): Two transesterification reactions; branchpoint A 2'-OH attacks 5' GU splice site forming a lariat. Group I & II introns self-splice (Group I uses free GMP/GTP; Group II forms lariat).",
                            "Translation Initiation: Prokaryotes use Shine-Dalgarno (16S rRNA) + fMet-tRNAfMet; Eukaryotes use 5'-cap eIF4E/4G + Kozak sequence + Met-tRNAiMet.",
                            "Peptidyl transferase is a ribozyme (23S rRNA in bacteria, 28S rRNA in eukaryotes). EF-Tu/eEF1A delivers aa-tRNA; EF-G/eEF2 drives translocation."
                        )
                    )
                ),
                keyFormulas = listOf(
                    "Wobble pairing (5' anticodon → 3' codon): Inosine (I) pairs with U, C, or A",
                    "Cost per peptide bond: 1 ATP (2 high-energy bonds for tRNA charging) + 2 GTP (EF-Tu + EF-G)"
                ),
                pyqPointers = listOf(
                    "Puromycin mimics aminoacyl-tRNA (tyrosyl-tRNA) in the A-site and causes premature chain termination in BOTH prokaryotes and eukaryotes.",
                    "Chloramphenicol & Erythromycin inhibit 50S; Tetracycline & Streptomycin inhibit 30S; Cycloheximide inhibits eukaryotic 60S; Diphtheria toxin ADP-ribosylates eEF2."
                )
            ),
            StudyTopic(
                id = "u3_operons_epigenetics",
                unitNumber = 3,
                title = "Operon Models (Lac, Trp, Lambda) & Epigenetics",
                subtitle = "Merodiploid analysis, attenuation, cI vs Cro, histone code",
                highYield = true,
                estimatedMinutes = 15,
                overview = "Classic CSIR Part-C genetics & molecular biology goldmine: predict β-galactosidase expression in partial diploids and decode chromatin marks.",
                sections = listOf(
                    ConceptSection(
                        heading = "Lac & Trp Operon Regulation",
                        bullets = listOf(
                            "Lac Operon (Negative inducible + Positive CAP-cAMP): Allolactose (inducer) binds LacI repressor. Low glucose → high cAMP → CAP binds upstream of promoter.",
                            "Merodiploid rules: I+ is trans-dominant over I-; Is (super-repressor, cannot bind allolactose) is trans-dominant uninducible; Oc (operator constitutive) is strictly cis-dominant.",
                            "Trp Operon: Repressible (TrpR + Trp corepressor) PLUS Transcription Attenuation via leader peptide (2 tandem Trp codons): High Trp → fast translation → 3:4 stem-loop terminator forms."
                        )
                    ),
                    ConceptSection(
                        heading = "Lambda Phage Lytic–Lysogenic Switch & Epigenetics",
                        bullets = listOf(
                            "cI repressor maintains lysogeny (activates PRM, represses PR/PL); Cro drives lytic cycle (represses PRM). High MOI & high cII favor lysogeny.",
                            "Histone Acetyltransferases (HATs) neutralize Lys+ charge → euchromatin (active); HDACs & H3K9me3 / H3K27me3 (Polycomb) → heterochromatin (repressed); H3K4me3 → active promoters."
                        )
                    )
                ),
                pyqPointers = listOf(
                    "In genotype F' I+ Oc Z- / I- O+ Z+, LacZ is INDUCIBLE (not constitutive!) because Oc is cis to Z- while O+ is cis to Z+ and I+ supplies trans repressor.",
                    "DNA methylation occurs at CpG islands via DNMT3A/3B (de novo) and DNMT1 (maintenance on hemimethylated DNA)."
                )
            )
        )
    ),
    CsirUnit(
        unitNumber = 4,
        title = "Cell Communication and Cell Signaling",
        shortTitle = "Signaling, Cancer & Immunology",
        partFocus = "Part B & C High-Yield",
        weightagePercent = 11,
        accentHex = 0xFF8C3B4A,
        summary = "GPCR/RTK/JAK-STAT/Wnt/Notch signaling, CAMs & ECM, intrinsic/extrinsic apoptosis, oncogenes vs tumor suppressors, and innate/adaptive immunology.",
        topics = listOf(
            StudyTopic(
                id = "u4_signaling_pathways",
                unitNumber = 4,
                title = "Cell Surface Receptors & Signal Transduction",
                subtitle = "GPCR, RTK-Ras-MAPK, JAK-STAT, Wnt, Hedgehog, Notch",
                highYield = true,
                estimatedMinutes = 16,
                overview = "Signal cascades convert extracellular ligands into rapid enzymatic or transcriptional responses via second messengers and regulated proteolysis.",
                sections = listOf(
                    ConceptSection(
                        heading = "GPCR, RTK & JAK-STAT",
                        bullets = listOf(
                            "GPCR (7-TM): Ligand acts as GEF → Gα exchanges GDP for GTP. Gαs activates adenylyl cyclase (↑cAMP → PKA); Gαi inhibits AC; Gαq activates PLCβ → IP3 (releases ER Ca2+) + DAG (activates PKC).",
                            "RTK: Ligand dimerization → trans-autophosphorylation on Tyr → SH2/PTB adaptors (Grb2) → Sos (GEF) → Ras-GTP → Raf → MEK → ERK (MAPK cascade).",
                            "JAK-STAT: Cytokine receptors lack intrinsic kinase; JAKs phosphorylate STATs → dimerize via SH2 → enter nucleus."
                        )
                    ),
                    ConceptSection(
                        heading = "Developmental Signaling Pathways",
                        bullets = listOf(
                            "Wnt/β-catenin: Without Wnt, GSK3β/APC/Axin phosphorylates β-catenin for proteasomal degradation. Wnt binds Frizzled/LRP → Dishevelled inhibits destruction complex → β-catenin enters nucleus (TCF/LEF).",
                            "Hedgehog: Without Hh, Patched inhibits Smoothened; Hh relieves Patched → Smoothened stabilizes active Gli.",
                            "Notch-Delta: Juxtacrine; γ-secretase cleaves Notch intracellular domain (NICD), which translocates directly to nucleus."
                        )
                    )
                ),
                pyqPointers = listOf(
                    "Cholera toxin locks Gαs in GTP-bound active state (ADP-ribosylation) → massive ↑cAMP; Pertussis toxin locks Gαi in GDP-bound inactive state → also ↑cAMP.",
                    "TGF-β pathway uses Ser/Thr kinase receptors (Type I & II) activating R-Smads (Smad2/3) + Co-Smad (Smad4)."
                )
            ),
            StudyTopic(
                id = "u4_apoptosis_cancer",
                unitNumber = 4,
                title = "Apoptosis, Cell Junctions & Cancer Biology",
                subtitle = "Caspases, Bcl-2 family, Cadherins/Integrins, Oncogenes & TSGs",
                highYield = true,
                estimatedMinutes = 14,
                overview = "Tissue homeostasis balances cell adhesion, regulated cell death, and proliferative checkpoints.",
                sections = listOf(
                    ConceptSection(
                        heading = "Apoptosis Pathways",
                        bullets = listOf(
                            "Extrinsic: FasL/TNF binds death receptor → FADD → Caspase-8/10 → Caspase-3/7.",
                            "Intrinsic (Mitochondrial): Cellular stress → BH3-only sensors (Bid, Bad) inhibit Bcl-2/Bcl-xL → Bax/Bak oligomerize on OMM → Cytochrome c release → Apaf-1 + dATP + Caspase-9 (Apoptosome).",
                            "Adhesion: Cadherins (Ca2+-dependent homophilic, adherens/desmosomes); Integrins (heterodimeric α/β, focal adhesions/hemidesmosomes to ECM)."
                        )
                    ),
                    ConceptSection(
                        heading = "Oncogenes vs Tumor Suppressors",
                        bullets = listOf(
                            "Oncogenes (Gain-of-function, dominant at cellular level): Ras (G12V blocks GAP), Myc, Src, HER2/ErbB2, BCR-ABL (Philadelphia chromosome t(9;22)).",
                            "Tumor Suppressors (Loss-of-function, Knudson Two-Hit recessive at cellular level): Rb, p53 (guardian of genome), APC, BRCA1/2, PTEN (PIP3 phosphatase)."
                        )
                    )
                ),
                pyqPointers = listOf(
                    "In C. elegans apoptosis: EGL-1 inhibits CED-9 (Bcl-2 homolog), releasing CED-4 (Apaf-1) to activate CED-3 (caspase).",
                    "Warburg effect: Cancer cells favor aerobic glycolysis even in presence of oxygen to feed biosynthetic intermediates."
                )
            ),
            StudyTopic(
                id = "u4_immunology",
                unitNumber = 4,
                title = "Innate & Adaptive Immunology",
                subtitle = "Antibody classes, MHC I vs II, V(D)J recombination, Hypersensitivity",
                highYield = true,
                estimatedMinutes = 16,
                overview = "High-scoring CSIR section covering antigen presentation, immunoglobulin structure, complement pathways, and immune disorders.",
                sections = listOf(
                    ConceptSection(
                        heading = "MHC Presentation & T-Cell Activation",
                        bullets = listOf(
                            "MHC Class I (all nucleated cells; α1-α3 + β2-microglobulin): Presents endogenous cytosolic peptides (8–10 aa) loaded via TAP1/2 to CD8+ cytotoxic T cells.",
                            "MHC Class II (APCs: DCs, Macrophages, B cells; α1α2 + β1β2): Presents exogenous endocytosed peptides (13–18 aa, CLIP exchange via HLA-DM) to CD4+ helper T cells.",
                            "T-cell activation requires Signal 1 (TCR–MHC) + Signal 2 (CD28–B7/CD80-86); CTLA-4 & PD-1 deliver inhibitory checkpoint signals."
                        )
                    ),
                    ConceptSection(
                        heading = "B-Cell Diversity & Hypersensitivity",
                        bullets = listOf(
                            "RAG1/RAG2 mediate V(D)J somatic recombination (12/23 rule); TdT adds N-nucleotides; AID drives somatic hypermutation & class switching.",
                            "Hypersensitivity Types: Type I (IgE/mast cells, anaphylaxis), Type II (IgG/IgM cytotoxic, hemolytic anemia), Type III (immune complexes, Arthus/SLE), Type IV (T-cell delayed, tuberculin test)."
                        )
                    )
                ),
                comparisonTable = StudyTable(
                    title = "Human Immunoglobulin Isotypes",
                    headers = listOf("Isotype", "Structure", "Key Functional Highlights"),
                    rows = listOf(
                        listOf("IgG", "Monomer", "Most abundant in serum; crosses placenta (FcRn); activates complement"),
                        listOf("IgM", "Pentamer (J-chain)", "First Ab in primary response; strongest complement activator"),
                        listOf("IgA", "Dimer (Secretory)", "Mucosal immunity (tears, saliva, breast milk); secretory component"),
                        listOf("IgE", "Monomer", "Binds FcεRI on mast cells/basophils; allergy & helminth defense"),
                        listOf("IgD", "Monomer", "Co-expressed with IgM on naive mature B-cell surface")
                    )
                ),
                pyqPointers = listOf(
                    "Papain cleaves IgG above hinge → 2 Fab + 1 Fc; Pepsin cleaves below hinge → 1 F(ab')2 + degraded Fc fragments.",
                    "Toll-like receptors: TLR3 (dsRNA), TLR4 (LPS), TLR5 (flagellin), TLR9 (unmethylated CpG DNA)."
                )
            )
        )
    )
)
