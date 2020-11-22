LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY controlCircuit IS --ControlCircuit choose arithmetic operation and set suitable input value at operation,ainvert and binvert--
	PORT( opcode:IN STD_LOGIC_VECTOR(0 TO 2);
			op :OUT STD_LOGIC_VECTOR(1 DOWNTO 0);
			ainv,binv,cin : OUT STD_LOGIC);
END controlCircuit;
ARCHITECTURE Behavior OF controlCircuit IS
BEGIN 
	PROCESS(opcode)
			BEGIN 
				IF opcode="000" THEN 
					op<="00";
					ainv<='0';
					binv<='0';
					cin<='0';
				ELSIF opcode="001" THEN 
					op<="01";
					ainv<='0';
					binv<='0';
					cin<='0';
				ELSIF opcode="011" THEN 
					op<="10";
					ainv<='0';
					binv<='0';
					cin<='0';
				ELSIF opcode="010" THEN 
					op<="10";
					ainv<='0';
					binv<='1';
					cin<='1';
				ELSIF opcode="101" THEN 
					op<="00";
					ainv<='1';
					binv<='1';
					cin<='0';
				ELSIF opcode="100" THEN 
					op<="11";
					ainv<='0';
					binv<='0';
					cin<='0';
			END IF;
		END PROCESS;
END Behavior;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY andgate2 IS
	PORT( z, q :IN STD_LOGIC;
			f : OUT STD_LOGIC);
END andgate2;
ARCHITECTURE Behavior OF andgate2 IS
BEGIN 
	f<=z AND q;
END Behavior;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY orgate2 IS 
	PORT( a2, b2 :IN STD_LOGIC;
			g : OUT STD_LOGIC);
END orgate2;
ARCHITECTURE Behavior OF orgate2 IS
BEGIN 
	g<=a2 OR b2;
END Behavior;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY xorgate2 IS
	PORT(a3,b3: IN STD_LOGIC;
			h:OUT STD_LOGIC);
END xorgate2;
ARCHITECTURE Behavior OF xorgate2 IS
BEGIN 
	h<=a3 XOR b3;
END Behavior;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY notgate1 IS
	PORT(a4: IN STD_LOGIC;
			n:OUT STD_LOGIC);
END notgate1;
ARCHITECTURE Behavior OF notgate1 IS
BEGIN 
	n <=NOT a4;
END Behavior;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY FULLADDER IS
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
ENTITY mux2to1 IS
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
ENTITY mux4to1 IS
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
		
		Stage0: notgate1 PORT MAP (a,n0);
		Stage1: mux2to1 PORT MAP (a,n0,ainvert,Ax);
		Stage2: notgate1 PORT MAP (b,n1);
		Stage3: mux2to1 PORT MAP (b,n1,binvert,Bx);
		Stage4: andgate2 PORT MAP (Ax,Bx,kai);
		Stage5: orgate2 PORT MAP (Ax,Bx,i);
		Stage6: xorgate2 PORT MAP (Ax,Bx,ni);
		Stage7: FULLADDER PORT MAP (Ax,Bx,cin,cout,sum);
		Stage8: mux4to1 PORT MAP(kai,i,sum,ni,operation(1 DOWNTO 0) ,res);
	END Structure;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY ALU16Bit IS
	PORT( a16,b16:IN STD_LOGIC_VECTOR(15 DOWNTO 0);
			opcode:IN STD_LOGIC_VECTOR(0 TO 2);
			result:OUT STD_LOGIC_VECTOR(15 DOWNTO 0);
			overflow: OUT STD_LOGIC);
END ALU16Bit;

ARCHITECTURE Structure2 OF ALU16Bit iS
	signal op :STD_LOGIC_VECTOR(1 DOWNTO 0);
	signal ainv, binv, cin : STD_LOGIC;
	signal carry : STD_LOGIC_VECTOR(15 DOWNTO 0);
	
	COMPONENT controlCircuit 
		PORT( opcode:IN STD_LOGIC_VECTOR(0 TO 2);
			op :OUT STD_LOGIC_VECTOR(1 DOWNTO 0);
			ainv,binv,cin : OUT STD_LOGIC);
	END COMPONENT;
	COMPONENT  ALU1Bit --ALU 1 Bit as component--
	PORT(a,b,cin,ainvert,binvert :IN STD_LOGIC;
			operation :IN  STD_LOGIC_VECTOR(1 DOWNTO 0);
			res,cout:OUT STD_LOGIC);
	END COMPONENT;
		BEGIN 	
			--caltulation--
			Stage0:ControlCircuit PORT MAP(opcode(0 TO 2),op(1 DOWNTO 0),ainv,binv,cin);
			Stage1:ALU1Bit PORT MAP(a16(0),b16(0),cin,ainv,binv,op(1 DOWNTO 0),result(0),carry(0));
			G1:FOR i 	in 1 	TO 15 GENERATE 
				Stages:ALU1Bit PORT MAP (a16(i),b16(i),carry(i-1),ainv,binv,op(1 DOWNTO 0),result(i),carry(i));
			END GENERATE;
			Stage17:overflow<=carry(15) XOR carry(14); --if overflow is 1 the result is not satisfying--  
END Structure2;