-- ============================================================================
-- ONLINE PET ADOPTION PLATFORM - DATABASE SCHEMA
-- Target DBMS: MySQL 8.0+ / MariaDB / H2 Database compatible
-- ============================================================================

DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS applications;
DROP TABLE IF EXISTS pets;
DROP TABLE IF EXISTS settings;
DROP TABLE IF EXISTS users;

-- 1. USERS TABLE
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL, -- 'ADMIN', 'SHELTER', 'ADOPTER'
    contact_info VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);

-- 2. PETS TABLE
CREATE TABLE pets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    shelter_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    breed VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    location VARCHAR(150) NOT NULL,
    description TEXT,
    photo_path VARCHAR(255) DEFAULT 'assets/images/default-pet.png',
    adoption_status VARCHAR(30) DEFAULT 'AVAILABLE', -- 'AVAILABLE', 'PENDING', 'ADOPTED'
    approval_status VARCHAR(30) DEFAULT 'PENDING',   -- 'PENDING', 'APPROVED', 'REJECTED'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pets_shelter FOREIGN KEY (shelter_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_pets_shelter ON pets(shelter_id);
CREATE INDEX idx_pets_adoption_status ON pets(adoption_status);
CREATE INDEX idx_pets_approval_status ON pets(approval_status);
CREATE INDEX idx_pets_type ON pets(type);
CREATE INDEX idx_pets_breed ON pets(breed);

-- 3. APPLICATIONS TABLE
CREATE TABLE applications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pet_id INT NOT NULL,
    adopter_id INT NOT NULL,
    shelter_id INT NOT NULL,
    details TEXT NOT NULL,
    status VARCHAR(30) DEFAULT 'PENDING', -- 'PENDING', 'APPROVED', 'REJECTED', 'CANCELLED'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_app_pet FOREIGN KEY (pet_id) REFERENCES pets(id) ON DELETE CASCADE,
    CONSTRAINT fk_app_adopter FOREIGN KEY (adopter_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_app_shelter FOREIGN KEY (shelter_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_app_pet ON applications(pet_id);
CREATE INDEX idx_app_adopter ON applications(adopter_id);
CREATE INDEX idx_app_shelter ON applications(shelter_id);
CREATE INDEX idx_app_status ON applications(status);

-- 4. MESSAGES TABLE
CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    application_id INT,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_msg_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_msg_receiver FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_msg_app FOREIGN KEY (application_id) REFERENCES applications(id) ON DELETE SET NULL
);

CREATE INDEX idx_msg_sender_receiver ON messages(sender_id, receiver_id);
CREATE INDEX idx_msg_app ON messages(application_id);

-- 5. SETTINGS TABLE
CREATE TABLE settings (
    setting_key VARCHAR(100) PRIMARY KEY,
    setting_value VARCHAR(500) NOT NULL
);
