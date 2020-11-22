ENTITY problem3 IS
	PORT (x1, x2, x3 :IN BIT;
			f: OUT BIT);
END problem3;

ARCHITECTURE Behavior OF problem3 IS 
BEGIN 
	f<=x2 OR (x3 AND (NOT x1));
END Behavior;