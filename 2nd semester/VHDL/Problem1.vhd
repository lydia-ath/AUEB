LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY andgate2 IS
	PORT( a, b: IN STD_logic;
			x : OUT STD_logic);
END andgate2;
 ARCHITECTURE  LogicFunc OF andgate2 IS
BEGIN
x <= a AND b ;
END LogicFunc;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY andgate5 IS
	PORT( a, b, c, d, e : IN STD_logic;
			x : OUT STD_logic);
END andgate5;
 ARCHITECTURE  LogicFunc OF andgate5 IS
BEGIN
x <= a AND b AND c AND d AND e ;
END LogicFunc;
LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY notgate IS
	PORT( a : IN STD_logic;
			x : OUT STD_logic);
END notgate;
ARCHITECTURE LogicFunc OF notgate IS
BEGIN
x <= NOT a;
END LogicFunc;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY orgate4 IS
	PORT(a,b,c,d : IN STD_logic;
			x : OUT STD_logic);
END orgate4;
ARCHITECTURE LogicFunc OF orgate4 IS
BEGIN
x <= a OR b OR c OR d;
end LogicFunc;		

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY orgate3 IS
	PORT(a,b,c: IN STD_logic;
			x : OUT STD_logic);
END orgate3;
ARCHITECTURE LogicFunc OF orgate3 IS
BEGIN
x <= a OR b OR c ;
end LogicFunc;	
			
LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY orgate2 IS
	PORT(a,b : IN STD_logic;
			x : OUT STD_logic);
END orgate2;
ARCHITECTURE LogicFunc OF orgate2 IS
BEGIN
x <= a OR b;
end LogicFunc; 

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY problem1  IS
	PORT ( x1, x2, x3, x4, x5 : IN STD_logic;
			f,g : OUT STD_logic	);
END problem1;
ARCHITECTURE Structure OF problem1 IS 
SIGNAL n1, n2, n3, n4, n5, o1, o2, o3, o4, o5, o6,g1 : STD_logic;

COMPONENT andgate2
	PORT(a, b : IN STD_logic;
		x: OUT STD_logic);
END COMPONENT;

COMPONENT andgate5
	PORT(a, b, c, d, e : IN STD_logic;
		x : OUT STD_logic);
END COMPONENT;

COMPONENT notgate
	PORT(a : IN STD_logic;
		x: OUT STD_logic);
END COMPONENT;

COMPONENT orgate4
	PORT(a, b, c, d : IN STD_logic;
		x : OUT STD_logic);
END COMPONENT;

COMPONENT orgate3
	PORT(a,b,c : IN STD_logic;
		x : OUT STD_logic);
END COMPONENT;

COMPONENT orgate2
	PORT(a, b : IN STD_logic;
		x : OUT STD_logic);
END COMPONENT;
BEGIN 
Stage0: NOTGATE PORT MAP (x1, n1);
Stage1: NOTGATE PORT MAP (x2, n2);
Stage2: NOTGATE PORT MAP (x3, n3);
Stage3: NOTGATE PORT MAP (x4, n4);
Stage4: NOTGATE PORT MAP (x5, n5);
Stage5: ORGATE4 PORT MAP (n1, n3, n4, x5, o1);
Stage6: ORGATE3 PORT MAP (x2, n3, n4, o2);			
Stage7: ORGATE3 PORT MAP (n1, x2, n5, o3);
Stage8: ORGATE3 PORT MAP (x2, n4, n5, o4);
Stage9: ORGATE3 PORT MAP (n2, x4, n5, o5);
Stage10: ANDGATE5 PORT MAP ( o1, o2, o3, o4, o5, g1);
Stage11: ORGATE2 PORT MAP (x3, x5, o6);
Stage13: ANDGATE2 PORT MAP (o6, g1, f);
Stage14: g <= g1;
END Structure;