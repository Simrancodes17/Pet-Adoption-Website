package com.petadoption.util;

import com.petadoption.model.BreedProfile;

import java.util.*;

/**
 * Educational directory providing detailed reference profiles for pet breeds.
 * Helps prospective adopters research animal size, temperament, care needs,
 * and household suitability before submitting an adoption application.
 */
public final class BreedDirectory {

    private static final List<BreedProfile> ALL_BREEDS = new ArrayList<>();

    static {
        // --- DOGS ---
        ALL_BREEDS.add(new BreedProfile(
                "Dog", "Labrador", "Large (55–80 lbs)",
                "Friendly, outgoing, even-tempered, and highly trainable",
                "High — requires 60+ minutes of daily exercise and interactive play",
                "Weekly brushing; regular nail trims and ear cleaning",
                "Active families, homes with yards, first-time and experienced owners",
                "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?auto=format&fit=crop&w=600&q=80",
                "One of the most beloved companion breeds worldwide, Labradors thrive on companionship, outdoor activities, and positive-reinforcement training."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Dog", "Golden Retriever", "Large (55–75 lbs)",
                "Gentle, patient, loyal, affectionate, and people-oriented",
                "High — enjoys brisk walks, swimming, and outdoor fetch",
                "Moderate to high; thorough brushing 2–3 times weekly to prevent mats",
                "Families with children, companion homes, therapy and service roles",
                "https://images.unsplash.com/photo-1552053831-71594a27632d?auto=format&fit=crop&w=600&q=80",
                "Renowned for their sweet temperament and reliability, Golden Retrievers form deep bonds with family members of all ages."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Dog", "German Shepherd", "Large (60–90 lbs)",
                "Confident, courageous, highly intelligent, and protective",
                "High — thrives on structured mental stimulation and physical agility",
                "Regular brushing; sheds seasonally (spring and autumn)",
                "Experienced owners, active households, structured environments",
                "https://images.unsplash.com/photo-1589941013453-ec89f33b5455?auto=format&fit=crop&w=600&q=80",
                "A versatile working and companion breed that requires consistent guidance, early socialization, and engaging mental challenges."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Dog", "Pug", "Small (14–18 lbs)",
                "Playful, charming, affectionate, and adaptable companion",
                "Low to moderate — short daily strolls and gentle indoor games",
                "Low coat grooming, but requires daily cleaning of facial skin folds",
                "Apartment dwellers, seniors, gentle families, low-activity lifestyles",
                "https://images.unsplash.com/photo-1517849845537-4d257902454a?auto=format&fit=crop&w=600&q=80",
                "Known for their comical personalities, Pugs thrive in cozy home environments where they can be close to their humans."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Dog", "Beagle", "Medium (20–30 lbs)",
                "Curious, merry, vocal, scent-driven, and sociable",
                "Moderate to high — loves scent walks and exploration",
                "Low; short coat requires quick weekly brushing",
                "Households with secure yards, patient owners who appreciate hounds",
                "https://images.unsplash.com/photo-1505628346881-b72b27e84530?auto=format&fit=crop&w=600&q=80",
                "Beagles are pack animals with an exceptional sense of smell and a cheerful disposition, benefiting from leashed walks and scent work."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Dog", "Husky", "Large (35–60 lbs)",
                "Energetic, independent, vocal, and pack-oriented",
                "Very high — requires extensive vigorous exercise and secure fencing",
                "Double coat requires weekly brushing and heavy shedding maintenance",
                "Athletic owners, cooler climates, runners and outdoor enthusiasts",
                "https://images.unsplash.com/photo-1605568427561-40dd23c2acea?auto=format&fit=crop&w=600&q=80",
                "Siberian Huskies are breathtaking, athletic dogs that need plenty of physical activity and strong containment to prevent wandering."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Dog", "Rottweiler", "Large (80–120 lbs)",
                "Loyal, calm, confident, protective, and deeply devoted",
                "Moderate — daily structured walks and obedience exercises",
                "Low coat maintenance; weekly brushing and regular ear checks",
                "Dedicated experienced dog guardians who prioritize early socialization",
                "https://images.unsplash.com/photo-1567752881298-894bb81f9379?auto=format&fit=crop&w=600&q=80",
                "Rottweilers are dependable companions that thrive with patient, reward-based training and consistent daily leadership."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Dog", "Shih Tzu", "Toy (9–16 lbs)",
                "Affectionate, playful, calm, and devoted companion",
                "Low to moderate — short indoor play sessions and light walks",
                "High coat grooming; daily brushing needed if kept in full coat",
                "Apartments, seniors, peaceful households seeking a loyal lap dog",
                "https://images.unsplash.com/photo-1541599540903-216a46ca1dc0?auto=format&fit=crop&w=600&q=80",
                "Bred specifically for companionship, the Shih Tzu is a gentle, trusting house dog that enjoys relaxed indoor companionship."
        ));

        // --- CATS ---
        ALL_BREEDS.add(new BreedProfile(
                "Cat", "Persian", "Medium (7–12 lbs)",
                "Quiet, gentle, placid, and serene indoor companion",
                "Low — prefers peaceful sunbathing and gentle petting",
                "Very high; daily combing required to prevent painful coat tangles",
                "Calm, quiet homes without turbulent activity or loud stressors",
                "https://images.unsplash.com/photo-1610878180933-123728745d22?auto=format&fit=crop&w=600&q=80",
                "Characterized by their luxurious long coats and round expressive eyes, Persians are tranquil felines that appreciate consistent care."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Cat", "Siamese", "Medium (6–10 lbs)",
                "Vocal, deeply affectionate, curious, and human-attached",
                "Moderate to high — loves climbing trees and puzzle toys",
                "Low; sleek short coat requires minimal brushing",
                "Owners who appreciate communicative, interactive companions",
                "https://images.unsplash.com/photo-1513360309081-38f0762daed1?auto=format&fit=crop&w=600&q=80",
                "Siamese cats form intense bonds with their human companions, often greeting their caretakers at the door and vocalizing their thoughts."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Cat", "Maine Coon", "Large (11–20+ lbs)",
                "Gentle giant, dog-like personality, friendly, and patient",
                "Moderate — retains a playful kittenish curiosity for years",
                "Moderate; brushing 2–3 times weekly keeps the silky coat pristine",
                "Families with kids, multi-pet households, larger living spaces",
                "https://images.unsplash.com/photo-1561948955-570b270e7c36?auto=format&fit=crop&w=600&q=80",
                "One of the oldest natural North American breeds, the Maine Coon is admired for its tufted ears, bushy tail, and friendly disposition."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Cat", "British Shorthair", "Medium to Large (9–16 lbs)",
                "Calm, easygoing, dignified, independent, and quiet",
                "Low to moderate — content with relaxed indoor routines",
                "Low; dense plush coat requires simple weekly brushing",
                "Busy professionals, quiet families, peaceful apartments",
                "https://images.unsplash.com/photo-1573865526739-10659fec78a5?auto=format&fit=crop&w=600&q=80",
                "Known for their round teddy-bear faces and dense coat, British Shorthairs enjoy being near family members without being demanding."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Cat", "Bengal", "Medium (8–15 lbs)",
                "Athletic, highly active, agile, intelligent, and curious",
                "High — needs vertical climbing perches, running wheels, or interactive wand games",
                "Low; short glittery pelt requires simple weekly brushing",
                "Energetic households looking for a dynamic, highly alert feline",
                "https://images.unsplash.com/photo-1518791841217-8f162f1e1131?auto=format&fit=crop&w=600&q=80",
                "Bengals exhibit striking wild-patterned rosettes and have high energy levels, often enjoying water and interactive agility challenges."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Cat", "Ragdoll", "Large (10–18 lbs)",
                "Docile, sweet-tempered, affectionate, and trusting",
                "Low to moderate — relaxed lap cat that follows owners room to room",
                "Moderate; semi-long coat has minimal undercoat and mats rarely",
                "Gentle families, indoor-only homes, first-time cat adopters",
                "https://images.unsplash.com/photo-1533738363-b7f9aef128ce?auto=format&fit=crop&w=600&q=80",
                "Ragdolls earned their name from their habit of relaxing completely limp in trusted arms, offering serene and gentle companionship."
        ));

        // --- RABBITS ---
        ALL_BREEDS.add(new BreedProfile(
                "Rabbit", "Holland Lop", "Small (2–4 lbs)",
                "Sweet, docile, calm, and curious with characteristic lop ears",
                "Moderate — requires daily exercise in a rabbit-proofed room",
                "Weekly brushing; increased during spring shedding seasons",
                "Indoor households, older children, gentle companion environments",
                "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=600&q=80",
                "One of the most popular house rabbit breeds, Holland Lops are compact, gentle companions that can be litter-box trained."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Rabbit", "Netherland Dwarf", "Miniature (1.5–2.5 lbs)",
                "Spirited, energetic, alert, and compact",
                "Moderate — enjoys tunnels, cardboard hides, and quiet exploration",
                "Low to moderate; simple brushing once weekly",
                "Adult households, quiet apartments, patient rabbit owners",
                "https://images.unsplash.com/photo-1535241749838-299277b6305f?auto=format&fit=crop&w=600&q=80",
                "The smallest domestic rabbit breed, Netherland Dwarfs are quick and inquisitive, thriving in calm, stress-free environments."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Rabbit", "Lionhead", "Small (2.5–3.5 lbs)",
                "Sociable, playful, intelligent, and friendly",
                "Moderate — enjoys foraging mats and safe indoor roaming",
                "High; distinctive woolly mane requires brushing 2–3 times weekly",
                "Dedicated rabbit lovers willing to spend time on coat grooming",
                "https://images.unsplash.com/photo-1518796745738-41048802f99a?auto=format&fit=crop&w=600&q=80",
                "Named for their distinct lion-like mane around the head, Lionheads are gentle and social companions that enjoy bonding with humans."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Rabbit", "Rex", "Medium (7–10 lbs)",
                "Gentle, calm, motherly, and notably velvety to the touch",
                "Moderate — needs daily free-roaming exercise outside their pen",
                "Low; unique plush plush coat needs only gentle hand wiping or soft brushing",
                "Family homes, gentle households, indoor companion pens",
                "https://images.unsplash.com/photo-1591561954557-26941169b49e?auto=format&fit=crop&w=600&q=80",
                "Celebrated for their luxurious velvet-like fur, Rex rabbits are affectionate, calm-tempered, and well-suited to indoor companion living."
        ));

        // --- BIRDS ---
        ALL_BREEDS.add(new BreedProfile(
                "Bird", "Parakeet", "Small (7–8 inches)",
                "Playful, social, chirpy, inquisitive, and easy to train",
                "Moderate — requires daily flight time in a secure enclosed room",
                "Daily fresh water and seeds, weekly cage lining and perch sanitization",
                "First-time bird adopters, family homes, apartment living",
                "https://images.unsplash.com/photo-1552728089-57bdde30beb3?auto=format&fit=crop&w=600&q=80",
                "Budgerigars (Parakeets) are bright, cheerful companion birds that can learn to mimic sounds and bond warmly with patient caregivers."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Bird", "Cockatiel", "Medium (12–13 inches)",
                "Affectionate, gentle, whistling, and social flock bird",
                "Moderate — enjoys out-of-cage perching and head scratches",
                "Daily nutrition, spacious cage, regular wing and beak checks",
                "Households seeking an interactive, musical, and sweet companion",
                "https://images.unsplash.com/photo-1598755257130-c2aaca1f061c?auto=format&fit=crop&w=600&q=80",
                "Cockatiels express their feelings through crest feathers and melodic whistles, forming deep bonds with their human flock."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Bird", "Lovebird", "Small (5–7 inches)",
                "Spirited, active, curious, and intensely loyal",
                "High — needs plenty of toys to chew, foraging, and interaction",
                "Daily cage maintenance, varied fresh produce and seed diet",
                "Dedicated bird caretakers with time for daily handling",
                "https://images.unsplash.com/photo-1522858547550-3405742f1607?auto=format&fit=crop&w=600&q=80",
                "Small parrots with big personalities, Lovebirds are energetic and engaging birds that thrive on attention and mental enrichment."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Bird", "Finch", "Small (4–5 inches)",
                "Peaceful, quiet, social, and delightful to observe",
                "Low handling — content living in spacious flight aviaries with flockmates",
                "Daily water and seed changes, roomy flight cage cleanings",
                "Busy households, quiet apartments, owners who prefer watching bird behavior",
                "https://images.unsplash.com/photo-1549608276-5786777e6587?auto=format&fit=crop&w=600&q=80",
                "Finches prefer socializing with other finches rather than direct human handling, making them ideal for tranquil indoor aviaries."
        ));

        // --- HAMSTERS ---
        ALL_BREEDS.add(new BreedProfile(
                "Hamster", "Syrian", "Medium (5–7 inches)",
                "Solitary, gentle when socialized, nocturnal, and quiet",
                "Moderate — requires large running wheel (11+ inch) and deep bedding",
                "Spot-cleaning bedding daily, full habitat clean every 2–3 weeks",
                "Single-hamster habitats, older kids with gentle handling skills",
                "https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=600&q=80",
                "Also called Golden Hamsters, Syrians MUST be housed strictly alone. They are active in evenings and make quiet bedroom companions."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Hamster", "Dwarf", "Small (3–4 inches)",
                "Quick, curious, energetic, and compact",
                "Moderate — enjoys sand baths, tunnel mazes, and evening foraging",
                "Sand bath maintenance, deep burrowing substrate, weekly spot cleans",
                "Owners with spacious horizontal terrarium setups",
                "https://images.unsplash.com/photo-1548767797-d8c844163c4c?auto=format&fit=crop&w=600&q=80",
                "Dwarf hamsters (such as Campbell's and Winter White) are fast and small, fascinating to watch as they dig burrows and forage seeds."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Hamster", "Roborovski", "Tiny (1.5–2 inches)",
                "Lightning fast, shy, energetic, and nocturnal",
                "High running stamina — runs miles every night on smooth spinner wheels",
                "Sand baths are essential; minimal handling due to speed",
                "Observation-focused owners with escape-proof glass enclosures",
                "https://images.unsplash.com/photo-1505672678857-92d8290aee04?auto=format&fit=crop&w=600&q=80",
                "The smallest and quickest of all domestic hamster species, Robos are delightful to observe scurrying through sand and tunnels."
        ));

        // --- TURTLES ---
        ALL_BREEDS.add(new BreedProfile(
                "Turtle", "Red-Eared Slider", "Aquatic (7–11 inches)",
                "Active swimmer, observant, diurnal, and long-lived (25+ yrs)",
                "Moderate — swims continuously and basks under UVB / heat lamps",
                "High filtration requirement; 75–100+ gallon tank, regular water testing",
                "Committed owners prepared for long-term aquatic care and filtration maintenance",
                "https://images.unsplash.com/photo-1508455858334-95337ba25607?auto=format&fit=crop&w=600&q=80",
                "Sliders are captivating semi-aquatic turtles requiring both swimming depth and a dry warm basking dock with full-spectrum UVB lighting."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Turtle", "Box Turtle", "Terrestrial (4–6 inches)",
                "Calm, shy initially, curious once comfortable, long-lived (30+ yrs)",
                "Moderate — requires spacious terrestrial pen with moist substrate and soaking pan",
                "Substrate misting, varied diet (greens, earthworms, insects), heat & UVB",
                "Patient reptile caretakers with space for indoor or secure outdoor pens",
                "https://images.unsplash.com/photo-1437622368342-7a3d73a34c8f?auto=format&fit=crop&w=600&q=80",
                "Box turtles are land dwellers featuring a hinged shell, requiring humidity control, shallow soaking water, and diverse dietary nutrition."
        ));
        ALL_BREEDS.add(new BreedProfile(
                "Turtle", "Russian Tortoise", "Terrestrial Tortoise (6–8 inches)",
                "Hardy, active digger, docile, strictly herbivorous, long-lived (40+ yrs)",
                "Moderate — enjoys burrowing and grazing in broad enclosures",
                "Dry substrate, deep soil/sand burrowing area, clean calcium-dusted greens",
                "Committed keepers seeking a small, manageable land tortoise",
                "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=600&q=80",
                "Russian Tortoises are resilient, compact herbivores that do not swim and thrive in wide wooden enclosures with proper heat gradients."
        ));
    }

    private BreedDirectory() {}

    public static List<BreedProfile> getAllBreeds() {
        return Collections.unmodifiableList(ALL_BREEDS);
    }

    public static List<BreedProfile> getBreedsBySpecies(String species) {
        if (species == null || species.isBlank() || species.equalsIgnoreCase("ALL")) {
            return getAllBreeds();
        }
        List<BreedProfile> filtered = new ArrayList<>();
        for (BreedProfile b : ALL_BREEDS) {
            if (b.getSpecies().equalsIgnoreCase(species.trim())) {
                filtered.add(b);
            }
        }
        return filtered;
    }
}
