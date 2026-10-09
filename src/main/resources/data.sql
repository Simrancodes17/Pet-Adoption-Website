-- Copy of data.sql in resources
INSERT INTO users (id, name, email, password_hash, role, contact_info) VALUES
(1, 'System Administrator', 'admin@petadoption.com', 'admin123', 'ADMIN', 'Admin HQ: 100 System Way, San Francisco, CA | (555) 019-2831'),
(2, 'Happy Tails Animal Rescue', 'shelter@happytails.org', 'shelter123', 'SHELTER', '450 Rescue Ave, Austin, TX | contact@happytails.org | (512) 555-0144'),
(3, 'Paws and Claws Sanctuary', 'contact@pawsandclaws.org', 'shelter123', 'SHELTER', '820 Greenfield Blvd, Denver, CO | info@pawsandclaws.org | (303) 555-0189'),
(4, 'John Doe', 'john.doe@example.com', 'adopter123', 'ADOPTER', '124 Oak Street, Austin, TX | Cell: (512) 555-7890'),
(5, 'Jane Smith', 'jane.smith@example.com', 'adopter123', 'ADOPTER', '789 Maple Drive, Denver, CO | Cell: (303) 555-4567'),
(6, 'Platform Admin', 'admin@pawhaven.org', 'password', 'ADMIN', 'PawHaven HQ | admin@pawhaven.org'),
(7, 'Austin Pets Alive', 'shelter1@pawhaven.org', 'password', 'SHELTER', '1156 W Cesar Chavez St, Austin, TX | (512) 555-1000'),
(8, 'Jane Doe', 'jane.doe@example.com', 'password', 'ADOPTER', '456 Cedar Ave, Austin, TX | (512) 555-9988');

INSERT INTO pets (id, shelter_id, name, type, breed, age, gender, location, description, photo_path, adoption_status, approval_status) VALUES
(1, 2, 'Bella', 'Dog', 'Golden Retriever', 2, 'Female', 'Austin, TX', 'Bella is a sweet, energetic Golden Retriever who loves belly rubs, long walks, and fetching tennis balls.', 'https://images.unsplash.com/photo-1552053831-71594a27632d?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(2, 2, 'Luna', 'Cat', 'Siamese', 1, 'Female', 'Austin, TX', 'Luna is a gorgeous Siamese with striking blue eyes. She is gentle, vocal, affectionate, and enjoys curling up in warm sunspots.', 'https://images.unsplash.com/photo-1513360309081-38f0762daed1?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(3, 2, 'Max', 'Dog', 'German Shepherd', 3, 'Male', 'Austin, TX', 'Max is an intelligent, loyal German Shepherd. He knows basic commands, is house-trained, and would thrive in an active home.', 'https://images.unsplash.com/photo-1589941013453-ec89f33b5e95?auto=format&fit=crop&w=600&q=80', 'PENDING', 'APPROVED'),
(4, 3, 'Milo', 'Cat', 'British Shorthair', 4, 'Male', 'Denver, CO', 'Milo is a calm, plush-coated British Shorthair who loves quiet environments, cozy blankets, and feather wands.', 'https://images.unsplash.com/photo-1573865526739-10659fec78a5?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(5, 3, 'Rocky', 'Dog', 'Labrador', 1, 'Male', 'Denver, CO', 'Rocky is a playful 1-year-old chocolate Labrador pup. Super friendly, loves water and toys. Awaiting platform verification.', 'https://images.unsplash.com/photo-1579783900882-c0d3dad7b119?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'PENDING'),
(6, 2, 'Coco', 'Rabbit', 'Holland Lop', 2, 'Female', 'Austin, TX', 'Coco is an adorable Holland Lop bunny with soft ears. Very social, loves fresh hay and leafy greens.', 'https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(7, 3, 'Charlie', 'Bird', 'Cockatiel', 1, 'Male', 'Denver, CO', 'Charlie is a lively whistling Cockatiel. Whistles cheerful tunes, steps up onto fingers.', 'https://images.unsplash.com/photo-1552728089-57bdde30beb3?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(8, 3, 'Daisy', 'Dog', 'Beagle', 2, 'Female', 'Denver, CO', 'Daisy has a gentle disposition and a wonderful hound nose. Friendly with all humans, spayed, and up-to-date on shots.', 'https://images.unsplash.com/photo-1537151625747-768eb6cf92b2?auto=format&fit=crop&w=600&q=80', 'ADOPTED', 'APPROVED'),
(9, 2, 'Otis', 'Dog', 'Pug', 2, 'Male', 'Austin, TX', 'Otis is a playful and comical Pug who adores snuggling on the sofa and greeting everyone he meets.', 'https://images.unsplash.com/photo-1517849845537-4d257902454a?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(10, 3, 'Ghost', 'Dog', 'Husky', 2, 'Female', 'Denver, CO', 'Ghost is a vocal, energetic Siberian Husky with bright blue eyes who loves cold weather and long hiking trails.', 'https://images.unsplash.com/photo-1534361960057-19889db9621e?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(11, 2, 'Titan', 'Dog', 'Rottweiler', 3, 'Male', 'Austin, TX', 'Titan is a gentle giant Rottweiler with excellent obedience training. Very affectionate and loyal protector.', 'https://images.unsplash.com/photo-1567752881298-894bb81f9379?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(12, 3, 'Teddy', 'Dog', 'Shih Tzu', 1, 'Female', 'Denver, CO', 'Teddy is a delightful lap dog with silky coat. Loves gentle brushing, indoor games, and warm laps.', 'https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(13, 2, 'Cleo', 'Cat', 'Persian', 2, 'Female', 'Austin, TX', 'Cleo is a luxurious white Persian cat with a sweet temperament. Very quiet, loves being pampered and brushed.', 'https://images.unsplash.com/photo-1610878180933-123728745d22?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(14, 3, 'Simba', 'Cat', 'Maine Coon', 3, 'Male', 'Denver, CO', 'Simba is an impressive, fluffy Maine Coon with tufted ears. Friendly with everyone, acts like a gentle puppy.', 'https://images.unsplash.com/photo-1561948955-570b270e7c36?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(15, 2, 'Kira', 'Cat', 'Bengal', 1, 'Female', 'Austin, TX', 'Kira is an athletic, leopard-spotted Bengal cat. Loves high perches, running wheels, and interactive play.', 'https://images.unsplash.com/photo-1518791841217-8f162f1e1131?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(16, 3, 'Oliver', 'Cat', 'Ragdoll', 2, 'Male', 'Denver, CO', 'Oliver is a classic Ragdoll who goes limp with contentment in your arms. Silky soft coat and deep blue eyes.', 'https://images.unsplash.com/photo-1533738363-b7f9aef128ce?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(17, 2, 'Pip', 'Rabbit', 'Netherland Dwarf', 1, 'Male', 'Austin, TX', 'Pip is a tiny Netherland Dwarf rabbit with lots of personality. Loves exploring cardboard tunnels and banana treats.', 'https://images.unsplash.com/photo-1518796745738-41048802f99a?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(18, 3, 'Leo', 'Rabbit', 'Lionhead', 2, 'Female', 'Denver, CO', 'Leo has an impressive wool mane around her head. Very docile and gentle, loves being petted behind her ears.', 'https://images.unsplash.com/photo-1535241749838-299277b6305f?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(19, 2, 'Velvet', 'Rabbit', 'Rex', 1, 'Male', 'Austin, TX', 'Velvet has an unbelievably soft, plush fur coat like velvet. Curious, friendly, and litter-box trained.', 'https://images.unsplash.com/photo-1591382696684-38c427c7547a?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(20, 3, 'Sky', 'Bird', 'Parakeet', 1, 'Female', 'Denver, CO', 'Sky is a vibrant sky-blue Parakeet (Budgerigar). Loves chirping cheerfully to music and eating millet sprays.', 'https://images.unsplash.com/photo-1522858547137-f1dcec554f55?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(21, 2, 'Sunny', 'Bird', 'Lovebird', 1, 'Male', 'Austin, TX', 'Sunny is a colorful Peach-faced Lovebird with a spirited personality. Loves ringing bells and perching on shoulders.', 'https://images.unsplash.com/photo-1544943910-4c1dc44a040b?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(22, 3, 'Robin', 'Bird', 'Finch', 1, 'Female', 'Denver, CO', 'Robin is a gentle Zebra Finch with pleasant beeping vocalizations. Thrives in an aviary with bird friends.', 'https://images.unsplash.com/photo-1452570053594-1b985d6ea890?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(23, 2, 'Peanut', 'Hamster', 'Syrian', 1, 'Male', 'Austin, TX', 'Peanut is a golden Syrian hamster who loves running on his silent spinner wheel and stuffing treats in his cheek pouches.', 'https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(24, 3, 'Mochi', 'Hamster', 'Dwarf', 1, 'Female', 'Denver, CO', 'Mochi is a tiny Winter White Dwarf hamster. Very active in the evenings, enjoys foraging for seeds and climbing.', 'https://images.unsplash.com/photo-1548767797-d8c844163c4c?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(25, 2, 'Speedy', 'Hamster', 'Roborovski', 1, 'Male', 'Austin, TX', 'Speedy is an energetic Roborovski hamster. The smallest hamster species, fascinating to watch scamper through sand baths.', 'https://images.unsplash.com/photo-1505672678857-92d8290aee04?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(26, 3, 'Shelly', 'Turtle', 'Red-Eared Slider', 3, 'Female', 'Denver, CO', 'Shelly is a healthy Red-Eared Slider aquatic turtle. Enjoys basking under UVB heat lamps and swimming gracefully.', 'https://images.unsplash.com/photo-1508455858334-95337ba25607?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(27, 2, 'Franklin', 'Turtle', 'Box Turtle', 4, 'Male', 'Austin, TX', 'Franklin is an Eastern Box Turtle who enjoys terrestrial enclosures with moist soil, earthworms, and fresh berries.', 'https://images.unsplash.com/photo-1437622368342-7a3d73a34c8f?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED'),
(28, 3, 'Tank', 'Turtle', 'Russian Tortoise', 5, 'Female', 'Denver, CO', 'Tank is a hardy Russian Tortoise. Loves wandering in her outdoor pen, munching dandelion greens and clover.', 'https://images.unsplash.com/photo-1518467166778-b88f373ffec7?auto=format&fit=crop&w=600&q=80', 'AVAILABLE', 'APPROVED');

INSERT INTO applications (id, pet_id, adopter_id, shelter_id, details, status) VALUES
(1, 3, 4, 2, 'I have a large fenced backyard, experience with working dog breeds, and work from home 4 days a week.', 'PENDING'),
(2, 8, 5, 3, 'Adopted Daisy last month. She has adjusted wonderfully to our family and loves our afternoon park outings.', 'APPROVED'),
(3, 1, 5, 2, 'Interested in adopting Bella for our family home. We have a 6-year-old child and a cat.', 'PENDING');

INSERT INTO messages (id, sender_id, receiver_id, application_id, content) VALUES
(1, 2, 4, 1, 'Hi John! Thank you for applying for Max. Could you tell us more about your daily exercise routine for active dogs?'),
(2, 4, 2, 1, 'Hello Happy Tails! Absolutely. I go on 45-minute morning jogs and have a secure 6ft fenced yard for playtime.'),
(3, 3, 5, 2, 'Congratulations Jane! Daisy has been officially approved for adoption. We are thrilled she found such a loving home.');

INSERT INTO settings (setting_key, setting_value) VALUES
('platform.name', 'PawHaven Online Pet Adoption Platform'),
('platform.tagline', 'Connecting loving homes with pets in need'),
('platform.contact_email', 'support@pawhaven.org'),
('platform.contact_phone', '+1 (800) 555-PAWS'),
('admin.auto_approve_pets', 'false'),
('adoptions.max_pending_per_user', '3'),
('notifications.async_email_enabled', 'true'),
('analytics.refresh_interval_minutes', '5');
