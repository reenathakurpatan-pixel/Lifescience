package com.example.data

import com.example.model.ConceptSection
import com.example.model.CsirUnit
import com.example.model.StudyTable
import com.example.model.StudyTopic

val units9To13: List<CsirUnit> = listOf(
    CsirUnit(
        unitNumber = 9,
        title = "Diversity of Life Forms",
        shortTitle = "Taxonomy & Biodiversity",
        partFocus = "Part B & Matching Matrix",
        weightagePercent = 7,
        accentHex = 0xFF3E7B6A,
        summary = "ICBN/ICZN nomenclature rules, Bacteria vs Archaea vs Eukarya, plant/animal diagnostic traits, larval forms, and Indian IUCN Red List species.",
        topics = listOf(
            StudyTopic(
                id = "u9_taxonomy_kingdoms_conservation",
                unitNumber = 9,
                title = "Taxonomy, Five Kingdoms & Indian Conservation",
                subtitle = "Nomenclature types, Bacteria/Archaea, Bryophytes-Angiosperms, Larvae & IUCN",
                highYield = true,
                estimatedMinutes = 14,
                overview = "Diagnostic morphological, biochemical, and ecological features regularly tested as match-the-following questions in CSIR Part B & C.",
                sections = listOf(
                    ConceptSection(
                        heading = "Nomenclature & Three-Domain System",
                        bullets = listOf(
                            "Type specimens: Holotype (single specimen designated by author), Isotype (duplicate of holotype), Lectotype (chosen later if holotype missing), Neotype (new specimen when all original material lost).",
                            "Archaea vs Bacteria: Archaea have ether-linked branched isoprenoid (phytanyl) monolayer/bilayer membranes, NO peptidoglycan (pseudomurein), introns in tRNA/rRNA, and initiate translation with Met (not fMet)."
                        )
                    ),
                    ConceptSection(
                        heading = "Plant & Animal Diagnostic Highlights",
                        bullets = listOf(
                            "Bryophytes (dominant haploid gametophyte, dependent sporophyte) vs Pteridophytes (dominant diploid sporophyte, vascular, homosporous/heterosporous) vs Gymnosperms (naked seeds, haploid endosperm) vs Angiosperms (double fertilization → 3n endosperm).",
                            "Key Larvae: Cnidaria (Planula), Platyhelminthes (Miracidium/Cercaria), Annelida/Mollusca (Trochophore), Crustacea (Nauplius), Echinodermata (Bipinnaria/Auricularia), Hemichordata (Tornaria).",
                            "Indian Threatened Fauna (IUCN): Great Indian Bustard (CR), Gharial (CR), Hangul / Kashmir Stag (CR), Snow Leopard (VU), Lion-tailed Macaque (EN), Red Panda (EN), One-horned Rhinoceros (VU)."
                        )
                    )
                ),
                pyqPointers = listOf(
                    "In ICBN (plants), tautonyms (e.g., Rattus rattus) are NOT allowed, whereas in ICZN (animals), tautonyms are valid.",
                    "Biodiversity hotspots in India (4): Himalaya, Indo-Burma, Western Ghats–Sri Lanka, Sundaland (Nicobar)."
                )
            )
        )
    ),
    CsirUnit(
        unitNumber = 10,
        title = "Ecological Principles",
        shortTitle = "Ecology, Populations & Biomes",
        partFocus = "Part B & C Graph/Formula",
        weightagePercent = 10,
        accentHex = 0xFF1B7A43,
        summary = "Niche theory, population growth & Lotka-Volterra models, survivorship curves, r/K life histories, diversity indices, island biogeography, and succession.",
        topics = listOf(
            StudyTopic(
                id = "u10_population_community_ecosystem",
                unitNumber = 10,
                title = "Population Dynamics, Community & Island Biogeography",
                subtitle = "Logistic growth, Lotka-Volterra isoclines, Shannon index & MacArthur-Wilson",
                highYield = true,
                estimatedMinutes = 16,
                overview = "Translate ecological equations and zero-growth isocline graphs directly into CSIR Part C answers.",
                sections = listOf(
                    ConceptSection(
                        heading = "Population Growth & Life History",
                        bullets = listOf(
                            "Exponential growth: dN/dt = rN (J-shaped, density-independent). Logistic growth: dN/dt = rN(1 - N/K) (S-shaped; max growth rate dN/dt occurs at N = K/2).",
                            "Survivorship Curves: Type I (convex, low early mortality — humans, elephants), Type II (diagonal, constant mortality — songbirds, Hydra), Type III (concave, high early mortality — oysters, trees).",
                            "r-selected (early maturity, many small offspring, unstable habitats) vs K-selected (late maturity, parental care, near K)."
                        )
                    ),
                    ConceptSection(
                        heading = "Lotka-Volterra Competition & Island Biogeography",
                        bullets = listOf(
                            "Coexistence occurs ONLY when intraspecific competition exceeds interspecific competition for BOTH species: K1 < K2/α21 AND K2 < K1/α12.",
                            "MacArthur-Wilson Island Biogeography: Equilibrium species richness (S*) rises with larger island area (lower extinction rate) and closer distance to mainland (higher immigration rate)."
                        )
                    )
                ),
                keyFormulas = listOf(
                    "Logistic: dN/dt = rN(K - N) / K   |   Inflection point: N = K/2",
                    "Shannon-Wiener Index: H' = -Σ (pi · ln pi)   |   Evenness: J' = H' / ln(S)",
                    "Simpson Index: D = Σ pi²  (Diversity = 1 - D)",
                    "Lindeman 10% Law: Ecological efficiency ≈ 10% across trophic levels"
                ),
                pyqPointers = listOf(
                    "Gause's Competitive Exclusion Principle: Two species with identical fundamental niches cannot coexist indefinitely on a single limiting resource.",
                    "Nitrogen cycle: Nitrosomonas (NH4+ → NO2-), Nitrobacter (NO2- → NO3-), Pseudomonas/Paracoccus (denitrification NO3- → N2)."
                )
            )
        )
    ),
    CsirUnit(
        unitNumber = 11,
        title = "Evolution and Behavior",
        shortTitle = "Evolution, HWE & Ethology",
        partFocus = "Part B & C Numericals",
        weightagePercent = 10,
        accentHex = 0xFF9E5A1E,
        summary = "Hardy-Weinberg equilibrium & forces, genetic drift, Kimura neutral theory, molecular clocks, speciation, and Hamilton's kin selection rule.",
        topics = listOf(
            StudyTopic(
                id = "u11_population_genetics_behavior",
                unitNumber = 11,
                title = "Hardy-Weinberg, Neutral Theory & Kin Selection",
                subtitle = "HWE numericals, F-statistics, Ka/Ks ratio, Hamilton's rule rB > C",
                highYield = true,
                estimatedMinutes = 15,
                overview = "Master Hardy-Weinberg allele frequency calculations, molecular evolution signatures, and altruism relatedness math.",
                sections = listOf(
                    ConceptSection(
                        heading = "Hardy-Weinberg & Evolutionary Forces",
                        bullets = listOf(
                            "Assumptions: Large random-mating population, NO mutation, NO migration, NO selection. Equilibrium reached in 1 generation of random mating.",
                            "Genetic drift (founder effect & bottleneck): Random sampling error strongest in small effective population size (Ne); decreases heterozygosity at rate 1/(2Ne) per generation.",
                            "Natural selection: Directional, Stabilizing (reduces variance), Disruptive (increases variance, favors extremes)."
                        )
                    ),
                    ConceptSection(
                        heading = "Molecular Evolution & Behavioral Ecology",
                        bullets = listOf(
                            "Ka/Ks (dN/dS) ratio: < 1 indicates purifying (negative) selection; = 1 indicates neutral evolution (Kimura); > 1 indicates positive (diversifying) selection.",
                            "Hamilton's Rule: Altruistic behavior evolves when rB > C (r = relatedness, B = benefit to recipient, C = cost to actor). In haplodiploid Hymenoptera, full sisters share r = 0.75!"
                        )
                    )
                ),
                keyFormulas = listOf(
                    "p + q = 1  |  p² + 2pq + q² = 1  |  Max heterozygosity (2pq = 0.5) at p = q = 0.5",
                    "Effective Population Size: Ne = (4 · Nm · Nf) / (Nm + Nf)",
                    "Coefficient of relatedness (r): Parent-offspring = 0.5, Full siblings = 0.5, Half-sibs = 0.25, First cousins = 0.125"
                ),
                pyqPointers = listOf(
                    "In a diploid population, if an autosomal recessive disease affects 1 in 10,000 (q² = 10⁻⁴), then q = 0.01, p = 0.99, and carrier frequency (2pq) ≈ 2% (0.0198).",
                    "Batesian mimicry: Harmless mimic resembles harmful model. Müllerian mimicry: Multiple harmful species share warning coloration."
                )
            )
        )
    ),
    CsirUnit(
        unitNumber = 12,
        title = "Applied Biology",
        shortTitle = "Biotech, Transgenics & Markers",
        partFocus = "Part B & C High-Yield",
        weightagePercent = 7,
        accentHex = 0xFF0E7490,
        summary = "Agrobacterium Ti plasmid, transgenic plants/animals, hybridoma mAbs, ELISA, bioremediation, molecular markers (RFLP/SSR/SNP), and gene therapy.",
        topics = listOf(
            StudyTopic(
                id = "u12_transgenics_markers_biotech",
                unitNumber = 12,
                title = "Agrobacterium, Molecular Markers & Bioprocessing",
                subtitle = "T-DNA borders, Vir genes, Co-dominant vs Dominant markers, Hybridoma HAT",
                highYield = true,
                estimatedMinutes = 14,
                overview = "Essential applied biotechnology mechanisms frequently paired with molecular techniques in Part C.",
                sections = listOf(
                    ConceptSection(
                        heading = "Agrobacterium-Mediated Transformation",
                        bullets = listOf(
                            "Wounded plant releases Acetosyringone → activates VirA/VirG two-component system → induces vir operon.",
                            "VirD1/D2 nicks 25-bp right & left T-DNA borders; VirE2 coats ssT-DNA; transferred via VirB Type IV secretion pilus into plant nucleus."
                        )
                    ),
                    ConceptSection(
                        heading = "Hybridoma & Molecular Markers",
                        bullets = listOf(
                            "HAT medium (Hypoxanthine, Aminopterin, Thymidine) selects fused B-cell + HGPRT- myeloma hybrids (unfused myeloma dies because Aminopterin blocks de novo purine synthesis).",
                            "Co-dominant markers (distinguish heterozygotes): RFLP, SSR (Microsatellites), CAPS, SNP. Dominant markers: RAPD, AFLP."
                        )
                    )
                ),
                pyqPointers = listOf(
                    "Bt toxin (Cry proteins from Bacillus thuringiensis) is a protoxin activated ONLY in the alkaline midgut of target insects, forming membrane pores.",
                    "Golden Rice expresses phytoene synthase (psy) + carotene desaturase (crtI) to accumulate β-carotene (provitamin A) in endosperm."
                )
            )
        )
    ),
    CsirUnit(
        unitNumber = 13,
        title = "Methods in Biology",
        shortTitle = "Techniques & Biostatistics",
        partFocus = "Highest Scoring Part C",
        weightagePercent = 12,
        accentHex = 0xFF6D28D9,
        summary = "PCR/qPCR, CRISPR-Cas9, Blotting, ChIP/Y2H, Confocal/TEM/FRAP/FRET, FACS, Spectroscopy (UV/CD/NMR/MS), Radiolabeling, and Biostatistics.",
        topics = listOf(
            StudyTopic(
                id = "u13_molecular_biophysical_methods",
                unitNumber = 13,
                title = "Molecular Cloning, Blotting, FACS & Spectroscopy",
                subtitle = "CRISPR, qPCR Ct, FRET/FRAP, Flow Cytometry, NMR/MS/CD",
                highYield = true,
                estimatedMinutes = 16,
                overview = "Unit 13 accounts for the largest cluster of experimental design & data interpretation questions in Part C.",
                sections = listOf(
                    ConceptSection(
                        heading = "Molecular & Interaction Techniques",
                        bullets = listOf(
                            "Southern (DNA), Northern (RNA), Western (Protein via antibody), Southwestern (DNA-binding protein).",
                            "qPCR: Lower Ct (cycle threshold) = higher initial template copy number. ΔΔCt calculates relative fold change (2^-ΔΔCt).",
                            "ChIP-seq maps in vivo protein-DNA binding; Yeast Two-Hybrid (GAL4 BD + AD) detects pairwise protein-protein interactions; Co-IP detects endogenous complexes.",
                            "CRISPR-Cas9: sgRNA + PAM (5'-NGG-3' for SpCas9) → blunt DSB 3 bp upstream of PAM → NHEJ (knockout) or HDR (knock-in)."
                        )
                    ),
                    ConceptSection(
                        heading = "Microscopy, FACS & Biophysical Tools",
                        bullets = listOf(
                            "FRET: Non-radiative dipole-dipole energy transfer (efficiency E ∝ 1 / (1 + (r/R0)^6)), measures 1–10 nm protein proximity.",
                            "FACS: Forward scatter (FSC) measures cell SIZE; Side scatter (SSC) measures internal GRANULARITY; Annexin V-FITC+/PI- marks early apoptosis.",
                            "MALDI-TOF / ESI-MS determines exact molecular mass; NMR solves 3D solution structures of small proteins (< 40 kDa); X-ray crystallography handles large complexes (needs crystals)."
                        )
                    )
                ),
                keyFormulas = listOf(
                    "Beer-Lambert Law: A = ε · c · l",
                    "FRET Efficiency: E = 1 / [1 + (r / R0)^6]",
                    "Resolution limit (Abbe): d = 0.61 · λ / NA"
                ),
                pyqPointers = listOf(
                    "In a FACS DNA-content histogram (Propidium Iodide): G1 peak = 2N, G2/M peak = 4N, S-phase lies between 2N and 4N, Sub-G1 (< 2N) = apoptotic DNA fragmentation.",
                    "Isoelectric focusing separates proteins by pI; SDS-PAGE separates strictly by molecular weight (~1.4 g SDS per g protein)."
                )
            ),
            StudyTopic(
                id = "u13_biostatistics",
                unitNumber = 13,
                title = "Biostatistics & Experimental Data Analysis",
                subtitle = "Mean/SD/SE, Normal/Binomial/Poisson, t-test, Chi-square & ANOVA",
                highYield = true,
                estimatedMinutes = 14,
                overview = "Choose the right statistical test for any CSIR Part C dataset and compute variance/standard error accurately.",
                sections = listOf(
                    ConceptSection(
                        heading = "Distributions & Measures of Dispersion",
                        bullets = listOf(
                            "Normal distribution: Mean = Median = Mode; Mean ± 1 SD = 68.2%, ± 2 SD = 95.4%, ± 3 SD = 99.7%.",
                            "Poisson distribution (rare discrete events): Mean (λ) = Variance (σ²).",
                            "Standard Error of Mean (SEM) = SD / √n (decreases as sample size n increases)."
                        )
                    ),
                    ConceptSection(
                        heading = "Hypothesis Testing Selection",
                        bullets = listOf(
                            "Parametric (normal data): Student's t-test (compare 2 means), One-way ANOVA (compare ≥ 3 means).",
                            "Non-parametric: Mann-Whitney U (2 independent), Wilcoxon signed-rank (2 paired), Kruskal-Wallis (≥ 3 groups).",
                            "Categorical counts: Chi-square (χ²) goodness-of-fit or test of independence."
                        )
                    )
                ),
                keyFormulas = listOf(
                    "χ² = Σ [(Observed - Expected)² / Expected]",
                    "SEM = s / √n   |   Coefficient of Variation (CV) = (SD / Mean) × 100%",
                    "Regression line: y = mx + c  |  Pearson r ∈ [-1, +1]"
                ),
                pyqPointers = listOf(
                    "p-value < α (e.g., 0.05) rejects the null hypothesis (H0). Type I error (α) = false positive (rejecting true H0); Type II error (β) = false negative; Power = 1 - β.",
                    "When Mean < Median < Mode, the distribution is negatively (left) skewed; when Mean > Median > Mode, it is positively (right) skewed."
                )
            )
        )
    )
)
