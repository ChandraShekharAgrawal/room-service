-- Create RoomType table
CREATE TABLE room_type (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    capacity INT NOT NULL,
    price INT NOT NULL
);


-- Insert initial data into RoomType table
-- INSERT INTO RoomType (id, name) VALUES (1, 'Single');
-- INSERT INTO RoomType (id, name) VALUES (2, 'Double');
-- INSERT INTO RoomType (id, name) VALUES (3, 'Suite');

-- Create Room table
CREATE TABLE room (
    id BIGSERIAL PRIMARY KEY,
    room_number VARCHAR(255) NOT NULL,
    room_type_id BIGINT,
    is_available BOOLEAN,
    FOREIGN KEY (room_type_id) REFERENCES room_type(id)
);

-- CREATE TABLE room_inventory (
--     id BIGSERIAL PRIMARY KEY,
--     room_id BIGINT,
--     availability_status BOOLEAN NOT NULL,
--     FOREIGN KEY (room_id) REFERENCES room(id)
-- );
