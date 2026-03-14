-- Lost Items (only columns that exist in your LostItem entity)
INSERT INTO lost_items (title, description, location, date, is_claimed, created_at) VALUES 
('Blue Water Bottle', 'Blue metal bottle with stickers', 'Library', CURRENT_TIMESTAMP(), false, CURRENT_TIMESTAMP()),
('Black Laptop', 'Dell XPS with charger', 'Student Center', CURRENT_TIMESTAMP(), false, CURRENT_TIMESTAMP()),
('Brown Wallet', 'Leather wallet with ID cards', 'Cafeteria', CURRENT_TIMESTAMP(), false, CURRENT_TIMESTAMP()),
('iPhone 13', 'Black iPhone with case', 'Parking Lot', CURRENT_TIMESTAMP(), false, CURRENT_TIMESTAMP()),
('Textbook', 'Calculus textbook', 'Room 201', CURRENT_TIMESTAMP(), false, CURRENT_TIMESTAMP());

-- Found Items (only columns that exist in your FoundItem entity)
INSERT INTO found_items (title, description, location, date, is_claimed, created_at) VALUES 
('Keys', 'Set of keys with blue keychain', 'Library', CURRENT_TIMESTAMP(), false, CURRENT_TIMESTAMP()),
('USB Drive', '32GB SanDisk USB', 'Computer Lab', CURRENT_TIMESTAMP(), false, CURRENT_TIMESTAMP()),
('Glasses', 'Prescription glasses in case', 'Bus stop', CURRENT_TIMESTAMP(), false, CURRENT_TIMESTAMP()),
('Headphones', 'Wireless Sony headphones', 'Gym', CURRENT_TIMESTAMP(), false, CURRENT_TIMESTAMP()),
('Umbrella', 'Blue folding umbrella', 'Library', CURRENT_TIMESTAMP(), false, CURRENT_TIMESTAMP());