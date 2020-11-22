--buzzer structural code
library IEEE, my_func;
use ieee.std_logic_1164.all;
use work.basic_func.all;

entity problem2 is
	port (DOOR, IGNITION, SBELT: in std_logic;
			WARNING: out std_logic);
end problem2;

architecture structural of problem2 is

		-- declaration of signals used to interconnect gates
	signal DOOR_NOT, SBELT_NOT, B1, B2: std_logic;

begin 
		-- Component instantiations statements
	U0: myNOT1 port map (DOOR, DOOR_NOT);
	U1: myNOT1 port map (SBELT, SBELT_NOT);
	U2: myAND2 port map (IGNITION, DOOR_NOT, B1);
	U3: myAND2 port map (IGNITION, SBELT_NOT, B2);
	U4: myOR2  port map (B1, B2, WARNING);
end structural;