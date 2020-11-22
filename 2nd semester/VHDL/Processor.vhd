--Processor
LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY controlCircuit IS --ControlCircuit choose arithmetic operation and set suitable input value at operation,ainvert and binvert--
	PORT( opcode:IN STD_LOGIC_VECTOR(0 TO 2);
			op :OUT STD_LOGIC_VECTOR(2 DOWNTO 0);
			ainv,binv,cin : OUT STD_LOGIC);
END controlCircuit;
ARCHITECTURE Behavior OF controlCircuit IS
BEGIN 
	PROCESS(opcode)
			BEGIN 
				IF opcode="010" THEN --AND--
					op<="000";
					ainv<='0';
					binv<='0';
					cin<='0';
				ELSIF opcode="011" THEN --OR--
					op<="001";
					ainv<='0';
					binv<='0';
					cin<='0';
				ELSIF opcode="100" THEN --ADD--
					op<="010";
					ainv<='0';
					binv<='0';
					cin<='0';
				ELSIF opcode="101" THEN --SUB--
					op<="010";
					ainv<='0';
					binv<='1';
					cin<='1';
				ELSIF opcode="101" THEN --NOR--
					op<="000";
					ainv<='1';
					binv<='1';
					cin<='0';
				ELSIF opcode="110" THEN --XOR--
					op<="100";
					ainv<='0';
					binv<='0';
					cin<='0';
				ELSIF opcode="111" THEN --NOT--
					op<="111";
					ainv<='1';
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
ENTITY mux5to1 IS
PORT ( w0, w1, w2, w3,w4 : IN STD_LOGIC ;
s : IN STD_LOGIC_VECTOR(2 DOWNTO 0) ;
f : OUT STD_LOGIC ) ;
END mux5to1 ;
ARCHITECTURE Behavior OF mux5to1 IS
BEGIN
	WITH s SELECT
	f <= w0 WHEN "000",
		w1 WHEN "001",
		w2 WHEN "010",
		w3 WHEN "100" ,
		w4 WHEN OTHERS;
END Behavior ;

LIBRARY ieee ;
USE ieee.std_logic_1164.all ;
ENTITY ALU1Bit IS
	PORT(a,b,cin,ainvert,binvert :IN STD_LOGIC;
			operation :IN  STD_LOGIC_VECTOR(2 DOWNTO 0);
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
	COMPONENT mux5to1
	PORT ( w0, w1, w2, w3,w4 : IN STD_LOGIC ;
	s : IN STD_LOGIC_VECTOR(2 DOWNTO 0) ;
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
		Stage8: mux5to1 PORT MAP(kai,i,sum,ni,Bx,operation(2 DOWNTO 0) ,res);
	END Structure;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
ENTITY ALU16Bit IS --h ALU16Bit exei epipleon dinatothta thn ektelesi ths praksis NOT--
	PORT( a16,b16:IN STD_LOGIC_VECTOR(15 DOWNTO 0);
			opcode:IN STD_LOGIC_VECTOR(0 TO 2);
			result:OUT STD_LOGIC_VECTOR(15 DOWNTO 0);
			overflow: OUT STD_LOGIC);
END ALU16Bit;


ARCHITECTURE Structure2 OF ALU16Bit iS
	signal op :STD_LOGIC_VECTOR(2 DOWNTO 0);
	signal ainv, binv, cin : STD_LOGIC;
	signal carry : STD_LOGIC_VECTOR(15 DOWNTO 0);
	
	COMPONENT controlCircuit 
		PORT( opcode:IN STD_LOGIC_VECTOR(0 TO 2);
			op :OUT STD_LOGIC_VECTOR(2 DOWNTO 0);
			ainv,binv,cin : OUT STD_LOGIC);
	END COMPONENT;
	COMPONENT  ALU1Bit --ALU 1 Bit as component--
	PORT(a,b,cin,ainvert,binvert :IN STD_LOGIC;
			operation :IN  STD_LOGIC_VECTOR(2 DOWNTO 0);
			res,cout:OUT STD_LOGIC);
	END COMPONENT;
		BEGIN 	
			--caltulation--
			Stage0:ControlCircuit PORT MAP(opcode(0 TO 2),op(2 DOWNTO 0),ainv,binv,cin);
			Stage1:ALU1Bit PORT MAP(a16(0),b16(0),cin,ainv,binv,op(2 DOWNTO 0),result(0),carry(0));
			G1:FOR i 	in 1 	TO 15 GENERATE 
				Stages:ALU1Bit PORT MAP (a16(i),b16(i),carry(i-1),ainv,binv,op(2 DOWNTO 0),result(i),carry(i));
			END GENERATE;
			Stage17:overflow<=carry(15) XOR carry(14); --if overflow is 1 the result is not satisfying-- 
 
END Structure2;

LIBRARY ieee;
USE ieee.std_logic_1164.all;
USE ieee.std_logic_signed.all;
ENTITY upcount IS 

  PORT ( Clear, Clock : IN STD_LOGIC; 
  Q : OUT STD_LOGIC_VECTOR(1 DOWNTO 0)); 
END upcount; 

ARCHITECTURE Behavior OF upcount IS 
 SIGNAL Count : STD_LOGIC_VECTOR(1 DOWNTO 0); 

BEGIN 
  PROCESS (Clock) 
  BEGIN 

    IF (Clock'EVENT AND Clock = '1') THEN 

     IF Clear = '1' THEN 
       Count <= "00"; 
     ELSE 
       Count <= Count + 1; 
     END IF; 

   END IF; 
  END PROCESS; 
  Q <= Count; 

END Behavior; 


LIBRARY ieee;
USE ieee.std_logic_1164.all;


ENTITY dec3to8 IS


  PORT ( W : IN STD_LOGIC_VECTOR(2 DOWNTO 0); 
  En : IN STD_LOGIC; 
  Y : OUT STD_LOGIC_VECTOR(0 TO 7)); 
END dec3to8; 

ARCHITECTURE Behavior OF dec3to8 IS 
BEGIN 
  PROCESS (W, En) 
  BEGIN 

    IF En = '1' THEN --decoder3to8(W, en)

      CASE W IS 
			WHEN "000" => Y<="10000000";
			WHEN "001" => Y<="01000000";
			WHEN "010" => Y<="00100000";
			WHEN "011" => Y<="00010000";
			WHEN "100" => Y<="00001000";
			WHEN "101" => Y<="00000100";
			WHEN "110" => Y<="00000010";
			WHEN "111" => Y<="00000001";
      END CASE; 
    ELSE 
      Y <= "00000000"; 
    END IF; 
   END PROCESS; 
END Behavior; 




LIBRARY ieee;
USE ieee.std_logic_1164.all;


ENTITY regn IS --register--
  GENERIC (n : INTEGER); 
  PORT ( R : IN STD_LOGIC_VECTOR(n-1 DOWNTO 0); 

  Rin, Clock,ClearR : IN STD_LOGIC; 

  Q : BUFFER STD_LOGIC_VECTOR(n-1 DOWNTO 0)); 
END regn; 

ARCHITECTURE Behavior OF regn IS 
BEGIN 
  PROCESS (Clock) 
  BEGIN
    IF Clock'EVENT AND Clock = '1' THEN 
		IF ClearR = '1' THEN
			Q <= (OTHERS => '0');
		ELSIF Rin = '1' THEN 
			  Q <=R; 
			END IF; 
    END IF; 
  END PROCESS; 
END Behavior; 


LIBRARY ieee; 
USE ieee.std_logic_1164.all; 
USE ieee.std_logic_signed.all; 

ENTITY proc IS 
    PORT ( DIN : IN STD_LOGIC_VECTOR(15 DOWNTO 0); 
           Resetn, Clock, Run: IN STD_LOGIC; 
           Done : BUFFER STD_LOGIC; 
           BusWires : BUFFER STD_LOGIC_VECTOR(15 DOWNTO 0)); 
END proc; 

ARCHITECTURE Behavior OF proc IS 
--signals--
SIGNAL Xreg,Yreg,Rin,Rout: STD_LOGIC_VECTOR(0 TO 7);
SIGNAL Clear , High ,IRin,overflow,ClearR:STD_LOGIC;
SIGNAL Extern , Ain , Gin, Gout:STD_LOGIC;
SIGNAL Count , I,op:STD_LOGIC_VECTOR(2 DOWNTO 0);
SIGNAL A,G,R0,R1,R2,R3,R4,R5,R6,R7,r: STD_LOGIC_VECTOR(15 DOWNTO 0);
SIGNAL IR: STD_LOGIC_VECTOR(8 DOWNTO 0);
SIGNAL Tstep_Q : STD_LOGIC_VECTOR(1 DOWNTO 0);
SIGNAL Sel:STD_LOGIC_VECTOR(9 DOWNTO 0);

--components--

COMPONENT ALU16Bit IS --ALU16Bit PORT MAP (A,BusWires,op,r,overflow);--
	PORT( a16,b16:IN STD_LOGIC_VECTOR(15 DOWNTO 0);
			opcode:IN STD_LOGIC_VECTOR(0 TO 2);
			result:OUT STD_LOGIC_VECTOR(15 DOWNTO 0);
			overflow: OUT STD_LOGIC);
END COMPONENT; 

COMPONENT upcount 
	PORT( Clear, Clock : IN STD_LOGIC; 
  Q : OUT STD_LOGIC_VECTOR(1 DOWNTO 0)); 
END COMPONENT;

COMPONENT dec3to8 
	PORT ( W : IN STD_LOGIC_VECTOR(2 DOWNTO 0); 
  En : IN STD_LOGIC; 
  Y : OUT STD_LOGIC_VECTOR(0 TO 7)); 
END COMPONENT; 

COMPONENT regn 
  GENERIC (n : INTEGER := 16); 
  PORT ( R : IN STD_LOGIC_VECTOR(n-1 DOWNTO 0); 
  Rin, Clock,ClearR : IN STD_LOGIC; 

  Q : BUFFER STD_LOGIC_VECTOR(n-1 DOWNTO 0)); 
END COMPONENT; 


BEGIN 
  High <= '1'; 
	Clear <= Resetn OR Done OR (NOT Run AND (NOT Tstep_Q(1)) AND (NOT Tstep_Q(0))) OR (overflow AND (NOT IR(2)) AND IR(1));
	ClearR <= overflow AND (NOT IR(2)) AND IR(1);

  Tstep: upcount PORT MAP (Clear,Clock, Tstep_Q); --auksanei to T kata 1
  I <= IR(8 DOWNTO 6);   --IR<=III--
	
  decX: dec3to8 PORT MAP (IR(5 DOWNTO 3), High, Xreg); --dexetai os eisodo to XXX k to apokodikopoiei--
  decY: dec3to8 PORT MAP (IR(2 DOWNTO 0), High, Yreg); --dexetai os eisodo to YYY k to apokodikopoiei --

	-- Instruction Table
	--  000: mv			Rx,Ry
	--  001: mvi		Rx,#D
	--  010: and        Rx,Ry	
	--  011: or         Rx,Ry	
	--  100: add		Rx,Ry				: Rx <- [Rx] + [Ry]
	--  101: sub		Rx,Ry				: Rx <- [Rx] - [Ry]
	--  110: xor        Rx,Ry	
	--  111: not        Rx,Ry
	-- 	OPCODE format: III XXX YYY, where 
	-- 	III = instruction, XXX = Rx, and YYY = Ry. For mvi,
	-- 	a second word of data is loaded from DIN

  controlsignals: PROCESS (Tstep_Q, I, Xreg, Yreg) 

  BEGIN 
  --initialize--
  Extern<='0';
  Done<='0';
  Ain<='0';
  Gin<='0';
  Gout<='0';
  IRin<='0';
  Rin<="00000000";
  Rout<="00000000";
    CASE Tstep_Q IS 
   
      WHEN "00" =>  --store DIN in IR as long as Tstep_Q = 0 
        IRin <= '1'; 
      WHEN "01" =>  --define signals in time step T1 
        CASE I IS 
				WHEN "000"=> --mv:Rx<-[Ry]--    
					Rout<=Yreg; 
					Rin<= Xreg;
					Done<='1';
				WHEN "001"=> --mvi:Rx<-DIN--
					Rin<=Xreg;
					Extern<='1';
					Done<='1';
				WHEN OTHERS => --energopoihse to Ain oste oti brisketai st bus na fortothei stn kataxoriti(regn) A
					Ain<='1';
					Rout<=Xreg;
        END CASE; 
      WHEN "10" =>  --define signals in time step T2 
        CASE I IS 
				WHEN "000"=> -- this stage doesn't have storing(stage mv:I0)
				WHEN "001"=> -- this stage doesn't have storing(stage mvi:I1)				
				WHEN OTHERS => 
					Rout<=Yreg;
					op<=I; --epilogi praksis--
					Gin<='1';
        END CASE; 
      WHEN "11" =>  --define signals in time step T3 
        CASE I IS 
				WHEN "000"=> -- this stage doesn't have storing(stage mv:I0)
				WHEN "001"=> -- this stage doesn't have storing(stage mvi:I1)
				WHEN OTHERS => 
					Gout<='1';
					Rin<=Xreg;
					Done<='1';
        END CASE; 
    END CASE; 
  END PROCESS; 
	--fortonontai stous kataxorites t katallhla dedomena--
	reg: regn GENERIC MAP (n => 9) PORT MAP (DIN(15 DOWNTO 7), IRin,Clock,ClearR,IR);
  reg_0: regn GENERIC MAP (n => 16) PORT MAP (BusWires, Rin(0), Clock,ClearR, R0); 
  reg_1: regn GENERIC MAP (n => 16) PORT MAP (BusWires, Rin(1), Clock,ClearR, R1); 
  reg_2: regn GENERIC MAP (n => 16) PORT MAP (BusWires, Rin(2), Clock,ClearR, R2); 
  reg_3: regn GENERIC MAP (n => 16) PORT MAP (BusWires, Rin(3), Clock,ClearR, R3); 
  reg_4: regn GENERIC MAP (n => 16) PORT MAP (BusWires, Rin(4), Clock,ClearR, R4); 
  reg_5: regn GENERIC MAP (n => 16) PORT MAP (BusWires, Rin(5), Clock,ClearR, R5); 
  reg_6: regn GENERIC MAP (n => 16) PORT MAP (BusWires, Rin(6), Clock,ClearR, R6); 
  reg_7: regn GENERIC MAP (n => 16) PORT MAP (BusWires, Rin(7), Clock,ClearR, R7); 
  
  --use ALU16BIT --
  reg_A: regn GENERIC MAP (n => 16) PORT MAP (BusWires, Ain, Clock,ClearR, A); --Aregn: apothikevei thn 1h timi h opoia prokeite na dothei os eisodos sthn alu,to antistixo a ths alu--
  Stage: ALU16Bit PORT MAP (A,BusWires,op,r,overflow);
  regG: regn GENERIC MAP (n => 16) PORT MAP (r,Gin,Clock,ClearR,G); --Gregn: apothikevei to apotelesma pou prokiptei apo thn praksi p epilegetai(meso tou opcode) na ginei metaksi t A k ths timhs pou vrisketai st bus--
  
  --We choose What We're going to put into bus.--
	Sel<=Rout&Gout&Extern;
	WITH Sel SELECT 
		BusWires <= R0 WHEN "1000000000",
			 R1 WHEN	"0100000000",
			 R2 WHEN	"0010000000",
			 R3 WHEN	"0001000000",
			 R4 WHEN	"0000100000",
			 R5 WHEN	"0000010000",
			 R6 WHEN	"0000001000",
			 R7 WHEN	"0000000100",
			 G WHEN	"0000000010",
			 DIN WHEN OTHERS;
END Behavior; 