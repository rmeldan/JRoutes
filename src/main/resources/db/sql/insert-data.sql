MERGE INTO user USING (VALUES(1, 'superadmin@admin.com', 'admin', false, 'super', '$2a$06$ztWgIgAXCESAsbDeoWjNpezG/WzKoYOf3z3jEGaNGZZ3bVK54Mg2W'))
   AS vals(a, b, c, d, e, f) ON user.id = vals.a
   WHEN NOT MATCHED THEN INSERT VALUES vals.a, vals.b, vals.c, vals.d, vals.e, vals.f;

MERGE INTO security_role USING (VALUES(1, 'ROLE_SUPER-ADMIN'))
   AS vals(a, b) ON security_role.id = vals.a
   WHEN NOT MATCHED THEN INSERT VALUES vals.a, vals.b;

MERGE INTO security_role USING (VALUES(2, 'ROLE_ADMIN'))
   AS vals(a, b) ON security_role.id = vals.a
   WHEN NOT MATCHED THEN INSERT VALUES vals.a, vals.b;

MERGE INTO security_role USING (VALUES(3, 'ROLE_USER'))
   AS vals(a, b) ON security_role.id = vals.a
   WHEN NOT MATCHED THEN INSERT VALUES vals.a, vals.b;

MERGE INTO user_security_ref USING (VALUES(1, 1), (1, 2), (1, 3))
   AS vals(a, b) ON user_security_ref.user_id = vals.a AND user_security_ref.security_role_id = vals.b
   WHEN NOT MATCHED THEN INSERT VALUES vals.a, vals.b;

MERGE INTO transport USING (VALUES(1, 'car'), (2, 'train'), (3, 'plane'))
   AS vals(a, b) ON transport.id = vals.a
   WHEN NOT MATCHED THEN INSERT VALUES vals.a, vals.b;

MERGE INTO country USING (VALUES(1, 'Ukraine'), (2, 'Belarus'), (3, 'Poland'))
   AS vals(a, b) ON country.id = vals.a
   WHEN NOT MATCHED THEN INSERT VALUES vals.a, vals.b;

MERGE INTO route_point USING (VALUES(1, false, 'Kiev', 1), (2, false, 'Minsk', 2), (3, false, 'Warsaw', 3))
   AS vals(a, b, c, d) ON route_point.id = vals.a
   WHEN NOT MATCHED THEN INSERT VALUES vals.a, vals.b, vals.c, vals.d;

MERGE INTO route_connection USING (VALUES
    (1, false, 44, 598, 1, 2, 1), (2, false, 52, 821, 1, 2, 2), (3, false, 123, 90, 1, 2, 3),
    (4, false, 40, 547, 1, 3, 1), (5, false, 70, 1006, 1, 3, 2), (6, false, 155, 205, 1, 3, 3),
    (7, false, 65, 892, 2, 3, 1), (8, false, 118, 1115, 2, 3, 2), (9, false, 263, 135, 2, 3, 3))
   AS vals(a, b, c, d, e, f, g) ON route_connection.id = vals.a
   WHEN NOT MATCHED THEN INSERT VALUES vals.a, vals.b, vals.c, vals.d, vals.e, vals.f, vals.g;