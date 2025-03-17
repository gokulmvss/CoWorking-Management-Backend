-- This file will be automatically executed when using Spring Boot with spring.jpa.hibernate.ddl-auto=create
-- Add initial roles or test data here as needed

-- Insert an admin user
-- Username: admin@example.com
-- Password: admin123 (BCrypt encoded)
INSERT INTO users (email, password, first_name, last_name, active, created_at)
SELECT 'admin@example.com', '$2a$10$XvYtl4shzwbIvGXuUgIXV.c.MMjX1plfzTgW7JU1YAGV9d1bEARMO', 'Admin', 'User', 1, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@example.com');

-- Insert user roles
INSERT INTO user_roles (user_id, role)
SELECT u.id, 'ADMIN' 
FROM users u 
WHERE u.email = 'admin@example.com'
AND NOT EXISTS (
    SELECT 1 FROM user_roles ur 
    JOIN users u2 ON ur.user_id = u2.id 
    WHERE u2.email = 'admin@example.com' AND ur.role = 'ADMIN'
);