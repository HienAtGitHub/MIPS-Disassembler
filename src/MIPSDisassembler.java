
public class MIPSDisassembler {

	public static void main(String[] args) {
		// Constant bitmask variables
		int mOpcode = 0xFC000000;
		int mSrcReg1 = 0x03E00000;
		int mSrcReg2 = 0x001F0000;
		int mDestReg = 0x0000F800;
		int mDestRegItype = 0x001F0000;
		int mFunc = 0x0000003F;
		int mOffset = 0x0000FFFF;
		
		// variables for MIPS instructions
		int opcode, srcReg1, srcReg2, destReg, func, destRegItype, branch_target_address;
		short offset;
		
		// array of MIPS instructions
		int[] instructions = new int[]{0x032BA020, 0x8CE90014, 0x12A90003, 
				0x022DA822, 0xADB30020, 0x02697824, 0xAE8FFFF4, 0x018C6020,
				0x02A4A825, 0x158FFFF7, 0x8ECDFFF0};
		// variable for hex address
		int default_hex_address = 0x9A040;
        
        for (int i:instructions)	
        {
        	String current_hex_address = Integer.toHexString(default_hex_address) + " ";
        	opcode = (i & mOpcode) >>> 26;
        	
        	//R-type format
        	if (opcode == 0)
    		{
    			srcReg1 = (i & mSrcReg1) >>> 21;
    			srcReg2 = (i & mSrcReg2) >>> 16;
    			destReg = (i & mDestReg) >>> 11;
    			func = i & mFunc;
    			if(func == 0x20)
    			{
    				current_hex_address += "add";
    			}
    			else if(func == 0x22)
    			{
    				current_hex_address += "sub";
    			}
    			else if(func == 0x24)
    			{
    				current_hex_address += "and";
    			}
    			else if(func == 0x25)
    			{
    				current_hex_address += "or";
    			}
    			else if(func == 0x2a)
    			{
    				current_hex_address += "slt";
    			}
    			else {
    				System.out.println("Error..");
    			}
    			System.out.println(current_hex_address + " $" + destReg + ", $" + srcReg1 + ", $" + srcReg2 );
    		}
        	//I-type format
    		else {
    			srcReg1 = (i & mSrcReg1) >>> 21;
    			destRegItype = (i & mDestRegItype) >>> 16;
    			offset = (short)(i & mOffset);
    			if (opcode == 0x23)
    			{
    				System.out.println(current_hex_address + "lw $" + destRegItype + ", " + offset + " ($" + srcReg1 + ")");
    			}
    			else if (opcode == 0x2b)
    			{
    				System.out.println(current_hex_address + "sw $" + destRegItype + ", " + offset + " ($" + srcReg1 + ")");
    			}
    			else if (opcode == 0x4)
    			{
    				branch_target_address = default_hex_address + 4 + (offset << 2);
    				System.out.println(current_hex_address + "beq $" + destRegItype +
    						", $" + srcReg1 + ", address " + Integer.toHexString(branch_target_address));
    			}
    			else if (opcode == 0x5)
    			{
    				branch_target_address = default_hex_address + 4 + (offset << 2);
    				System.out.println(current_hex_address + "bne $" + destRegItype +
    						", $" + srcReg1 + ", address " + Integer.toHexString(branch_target_address));
    			}
    			else {
    				System.out.println("Not Found..");
    			}
    		}
        	default_hex_address += 4;
        	
        }
		
		}

	}

