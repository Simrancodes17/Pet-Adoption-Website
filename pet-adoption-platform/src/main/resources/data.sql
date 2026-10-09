-- Copy of data.sql in resources
INSERT INTO users (id, name, email, password_hash, role, contact_info) VALUES
(1, 'System Administrator', 'admin@petadoption.com', 'admin123', 'ADMIN', 'Admin HQ: 100 System Way, San Francisco, CA | (555) 019-2831'),
(2, 'Happy Tails Animal Rescue', 'shelter@happytails.org', 'shelter123', 'SHELTER', '450 Rescue Ave, Austin, TX | contact@happytails.org | (512) 555-0144'),
(3, 'Paws and Claws Sanctuary', 'contact@pawsandclaws.org', 'shelter123', 'SHELTER', '820 Greenfield Blvd, Denver, CO | info@pawsandclaws.org | (303) 555-0189'),
(4, 'John Doe', 'john.doe@example.com', 'adopter123', 'ADOPTER', '124 Oak Street, Austin, TX | Cell: (512) 555-7890'),
(5, 'Jane Smith', 'jane.smith@example.com', 'adopter123', 'ADOPTER', '789 Maple Drive, Denver, CO | Cell: (303) 555-4567');

INSERT INTO pets (id, shelter_id, name, type, breed, age, location, description, photo_path, adoption_status, approval_status) VALUES
(1, 2, 'Bella', 'Dog', 'Golden Retriever', 2, 'Austin, TX', 'Bella is a sweet, energetic Golden Retriever who loves belly rubs, long walks, and fetching tennis balls.', 'assets/images/golden-retriever.jpg', 'AVAILABLE', 'APPROVED'),
(2, 2, 'Luna', 'Cat', 'Siamese', 1, 'Austin, TX', 'Luna is a gorgeous Siamese with striking blue eyes. She is gentle, vocal, affectionate, and enjoys curling up in warm sunspots.', 'assets/images/siamese-cat.jpg', 'AVAILABLE', 'APPROVED'),
(3, 2, 'Max', 'Dog', 'German Shepherd', 3, 'Austin, TX', 'Max is an intelligent, loyal German Shepherd. He knows basic commands, is house-trained, and would thrive in an active home.', 'assets/images/german-shepherd.jpg', 'PENDING', 'APPROVED'),
(4, 3, 'Milo', 'Cat', 'British Shorthair', 4, 'Denver, CO', 'Milo is a calm, plush-coated British Shorthair who loves quiet environments, cozy blankets, and feather wands.', 'assets/images/british-shorthair.jpg', 'AVAILABLE', 'APPROVED'),
(5, 3, 'Rocky', 'Dog', 'Labrador Retriever', 1, 'Denver, CO', 'Rocky is a playful 1-year-old chocolate Lab pup. Super friendly, loves water and toys. Awaiting platform verification.', 'assets/images/chocolate-lab.jpg', 'AVAILABLE', 'PENDING'),
(6, 2, 'Coco', 'Rabbit', 'Holland Lop', 2, 'Austin, TX', 'Coco is an adorable Holland Lop bunny with soft ears. Very social, loves fresh hay and leafy greens.', 'assets/images/holland-lop.jpg', 'AVAILABLE', 'APPROVED'),
(7, 3, 'Charlie', 'Bird', 'Cockatiel', 1, 'Denver, CO', 'Charlie is a lively whistling Cockatiel. Whistles cheerful tunes, steps up onto fingers.', 'assets/images/cockatiel.jpg', 'AVAILABLE', 'APPROVED'),
(8, 3, 'Daisy', 'Dog', 'Beagle', 2, 'Denver, CO', 'Daisy has a gentle disposition and a wonderful hound nose. Friendly with all humans, spayed, and up-to-date on shots.', 'assets/images/beagle.jpg', 'ADOPTED', 'APPROVED');

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
