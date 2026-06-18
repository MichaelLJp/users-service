-- DML usuarios Relatos de Papel
-- Las contrasenas estan cifradas con BCrypt (coste 10).
--   admin@relatos.com -> admin123
--   cliente@relatos.com -> user123
USE users_db;
GO

SET IDENTITY_INSERT users ON;

INSERT INTO users (id, nombre, email, password, role, created_at) VALUES
(1, N'Administrador', N'admin@relatos.com',   N'$2b$10$jB1n2BMmTU4.pjgkRrTwBeFBr1P2tjkpl5JE9OBVpVcoA5/318rRe', N'ADMIN', SYSDATETIME()),
(2, N'Cliente Demo',  N'cliente@relatos.com', N'$2b$10$16b69hexoXYEGkjmBI0LCuooyYfJsShHh089gEznHvNWwTCNp1C86', N'USER',  SYSDATETIME());

SET IDENTITY_INSERT users OFF;
GO
