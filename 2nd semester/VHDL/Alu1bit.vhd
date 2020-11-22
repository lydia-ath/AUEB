LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY andgate2 IS --and--
	PORT( z, q :IN STD_LOGIC;
			f : OUT STD_LOGIC);
END andgate2;
ARCHITECTURE Behavior OF andgate2 IS
BEGIN 
	f<=z AND q;
END Behavior;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY orgate2 IS --or--
	PORT( a2, b2 :IN STD_LOGIC;
			g : OUT STD_LOGIC);
END orgate2;
ARCHITECTURE Behavior OF orgate2 IS
BEGIN 
	g<=a2 OR b2;
END Behavior;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY xorgate2 IS --xor--
	PORT(a3,b3: IN STD_LOGIC;
			h:OUT STD_LOGIC);
END xorgate2;
ARCHITECTURE Behavior OF xorgate2 IS
BEGIN 
	h<=a3 XOR b3;
END Behavior;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY notgate1 IS --not--
	PORT(a4: IN STD_LOGIC;
			n:OUT STD_LOGIC);
END notgate1;
ARCHITECTURE Behavior OF notgate1 IS
BEGIN 
	n <=NOT a4;
END Behavior;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY FULLADDER IS --full adder-
	PORT(a5,b5,cin: IN STD_LOGIC;
			cout,sum:OUT STD_LOGIC);
END FULLADDER;
ARCHITECTURE Behavior OF FULLADDER IS
BEGIN 
	cout<=(b5 AND cin)OR(a5 AND cin)OR(a5 AND b5);
	sum<=(a5 AND(NOT b5)AND (NOT cin))OR((NOT a5) AND b5 AND (NOT cin))OR ((NOT a5) AND(NOT b5)AND  cin) OR (a5 AND b5 AND cin);
END Behavior;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY mux2to1 IS --multiplexer 2 to 1--
	PORT(y0,y1,s: IN STD_LOGIC;
			m:OUT STD_LOGIC);
END mux2to1;
ARCHITECTURE Behavior OF mux2to1 IS
BEGIN 
	WITH s SELECT 
		m<= y0 WHEN '0',
			y1 WHEN OTHERS;
END Behavior;

LIBRARY ieee ;
USE ieee.std_logic_1164.all ;
ENTITY mux4to1 IS --multiplexer 4 to 1--
PORT ( w0, w1, w2, w3 : IN STD_LOGIC ;
s : IN STD_LOGIC_VECTOR(1 DOWNTO 0) ;
f : OUT STD_LOGIC ) ;
END mux4to1 ;
ARCHITECTURE Behavior OF mux4to1 IS
BEGIN
	WITH s SELECT
	f <= w0 WHEN "00",
	w1 WHEN "01",
	w2 WHEN "10",
w3 WHEN OTHERS ;
END Behavior ;

LIBRARY ieee ;
USE ieee.std_logic_1164.all ;
ENTITY ALU1Bit IS 
	PORT(a,b,cin,ainvert,binvert :IN STD_LOGIC;
			operation :IN  STD_LOGIC_VECTOR(1 DOWNTO 0);
			res,cout:OUT STD_LOGIC);
END ALU1Bit;

ARCHITECTURE Structure OF ALU1Bit IS
	SIGNAL n0, Ax, n1, Bx, kai, i, ni, sum : STD_LOGIC;
	--components:--
	COMPONENT andgate2
		PORT(z,q:IN STD_LOGIC;
				f:OUT STD_LOGIC);
	END COMPONENT;
	COMPONENT orgate2
		PORT(a2,b2:IN STD_LOGIC;
				g:OUT STD_LOGIC);
	END COMPONENT;
	COMPONENT xorgate2
		PORT(a3,b3:IN STD_LOGIC;
				h:OUT STD_LOGIC);
	END COMPONENT;
	
	COMPONENT notgate1
		PORT(a4:IN STD_LOGIC;
				n:OUT STD_LOGIC);
	END COMPONENT;
	COMPONENT FULLADDER
		PORT(a5,b5,cin: IN STD_LOGIC;
			cout,sum:OUT STD_LOGIC);
	END COMPONENT;
	COMPONENT mux2to1
		PORT(y0,y1:IN STD_LOGIC;
				s:IN STD_LOGIC;
				m:OUT STD_LOGIC);
	END COMPONENT;
	COMPONENT mux4to1
	PORT ( w0, w1, w2, w3 : IN STD_LOGIC ;
	s : IN STD_LOGIC_VECTOR(1 DOWNTO 0) ;
	f : OUT STD_LOGIC ) ;
	END COMPONENT;
	
	BEGIN 
		
		Stage0: notgate1 PORT MAP (a,n0); --crete a'--
		Stage1: mux2to1 PORT MAP (a,n0,ainvert,Ax); --multiplexer choose a or a'--
		Stage2: notgate1 PORT MAP (b,n1); --creat b'--
		Stage3: mux2to1 PORT MAP (b,n1,binvert,Bx); --multiplexer choose b or b'--
		Stage4: andgate2 PORT MAP (Ax,Bx,kai); --a and b--
		Stage5: orgate2 PORT MAP (Ax,Bx,i); --a or b--
		Stage6: xorgate2 PORT MAP (Ax,Bx,ni); --a xor b--
		Stage7: FULLADDER PORT MAP (Ax,Bx,cin,cout,sum); --a add b--
		Stage8: mux4to1 PORT MAP(kai,i,sum,ni,operation(1 DOWNTO 0) ,res); --multiplexer choose AND or OR or XOR or FULLADDER gate--
	END Structure;