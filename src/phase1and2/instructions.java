/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phase1and2;

import static phase1and2.execute.gpr;
import static phase1and2.execute.spr;
import static phase1and2.loadProcess.mem;
import static phase1and2.start.R;
import static phase1and2.start.ps;

/**
 *
 * @author Huda
 */
class instructions {

    public void MOV(byte R1, byte R2) {
        gpr.generalPurposeRegisters[R1] = gpr.generalPurposeRegisters[R2];
    }

    public void MOVI(byte R1, short imm) {
        gpr.generalPurposeRegisters[R1] = imm;
    }

    public void ADD(byte R1, byte R2) {
        if ((gpr.generalPurposeRegisters[R1] + gpr.generalPurposeRegisters[R2]) >= 32767 || (gpr.generalPurposeRegisters[R1] + gpr.generalPurposeRegisters[R2]) < -32768) {
            spr.flag.set(12, true);
        }

        if ((gpr.generalPurposeRegisters[R1] + gpr.generalPurposeRegisters[R2]) == 0) {
            spr.flag.set(14, 1);
        }
        gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] + gpr.generalPurposeRegisters[R2]);

        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
            spr.flag.set(13, true);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
            spr.flag.set(13, false);
        }

    }

    public void ADDI(byte R1, short imm) {
        if ((gpr.generalPurposeRegisters[R1] + imm) >= 32767 || (gpr.generalPurposeRegisters[R1] + imm) < -32768) {
            spr.flag.set(12, true);
        }

        if ((gpr.generalPurposeRegisters[R1] + imm) == 0) {
            spr.flag.set(14, 1);
        }
        gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] + imm);

        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
            spr.flag.set(13, true);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
            spr.flag.set(13, false);
        }
    }

    public void SUB(byte R1, byte R2) {
        if ((gpr.generalPurposeRegisters[R1] - gpr.generalPurposeRegisters[R2]) >= 32767 || (gpr.generalPurposeRegisters[R1] - gpr.generalPurposeRegisters[R2]) < -32768) {
            spr.flag.set(12, true);
        }

        if ((gpr.generalPurposeRegisters[R1] - gpr.generalPurposeRegisters[R2]) == 0) {
            spr.flag.set(14, true);

            gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] - gpr.generalPurposeRegisters[R2]);

        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
            spr.flag.set(13, true);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
            spr.flag.set(13, false);
        }

    }

    public void SUBI(byte R1, short imm) {
        if ((gpr.generalPurposeRegisters[R1] - imm) >= 32767 || (gpr.generalPurposeRegisters[R1] - imm) < -32768) {
            spr.flag.set(12, true);
        }

        if ((gpr.generalPurposeRegisters[R1] - imm) == 0) {
            spr.flag.set(14, true);

            gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] - imm);

        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
            spr.flag.set(13, true);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
            spr.flag.set(13, false);
        }

    }

    public void MUL(byte R1, byte R2) {
        if ((gpr.generalPurposeRegisters[R1] * gpr.generalPurposeRegisters[R2]) == 0) {
            spr.flag.set(14, true);
        }

        if ((gpr.generalPurposeRegisters[R1] * gpr.generalPurposeRegisters[R2]) >= 32767 || (gpr.generalPurposeRegisters[R1] * gpr.generalPurposeRegisters[R2]) < -32768) {
            spr.flag.set(12, true);
        }

        gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] * gpr.generalPurposeRegisters[R2]);

        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
            spr.flag.set(13, true);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
            spr.flag.set(13, false);
        }

    }

    public void MULI(byte R1, short imm) {
        if ((gpr.generalPurposeRegisters[R1] * imm) == 0) {
            spr.flag.set(14, true);
        }

        if ((gpr.generalPurposeRegisters[R1] * imm) >= 32767 || (gpr.generalPurposeRegisters[R1] * imm) < -32768) {
            spr.flag.set(12, true);
        }

        gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] * imm);

        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
            spr.flag.set(13, true);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
            spr.flag.set(13, false);
        }

    }

    public void DIV(byte R1, byte R2) {
        if (gpr.generalPurposeRegisters[R1] == 0) {
            spr.flag.set(14, true);
        }
        try {
            if (gpr.generalPurposeRegisters[R2] == 0) {
                throw new ArithmeticException();
            }
            if ((gpr.generalPurposeRegisters[R1] / gpr.generalPurposeRegisters[R2]) >= 32767 || (gpr.generalPurposeRegisters[R1] / gpr.generalPurposeRegisters[R2]) < -32768) {
                spr.flag.set(12, true);
            }
            gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] / gpr.generalPurposeRegisters[R2]);
            if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
                spr.flag.set(13, true);
            }
            if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
                spr.flag.set(13, false);
            }

        } catch (ArithmeticException ex) {
            System.out.println("Error: Division by zero");
        }
    }

    public void DIVI(byte R1, short imm) {
        if (gpr.generalPurposeRegisters[R1] == 0) {
            spr.flag.set(14, true);
        }
        try {
            if (imm == 0) {
                throw new ArithmeticException();
            }
            if ((gpr.generalPurposeRegisters[R1] / imm) >= 32767 || (gpr.generalPurposeRegisters[R1] / imm) < -32768) {
                spr.flag.set(12, true);
            }
            gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] / imm);
            if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
                spr.flag.set(13, true);
            }
            if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
                spr.flag.set(13, false);
            }

        } catch (ArithmeticException ex) {
            System.out.println("Error: Division by zero");
        }
    }

    public void AND(byte R1, byte R2) {
        gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] & gpr.generalPurposeRegisters[R2]);
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
            spr.flag.set(13, true);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
            spr.flag.set(13, false);
        }
    }

    public void ANDI(byte R1, short imm) {
        gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] & imm);
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
            spr.flag.set(13, true);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
            spr.flag.set(13, false);
        }
    }

    public void OR(byte R1, byte R2) {
        gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] | gpr.generalPurposeRegisters[R2]);
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
            spr.flag.set(13, true);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
            spr.flag.set(13, false);
        }
    }

    public void ORI(byte R1, short imm) {
        gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] | imm);
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
            spr.flag.set(13, true);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
            spr.flag.set(13, false);
        }
    }

    public void BZ(short R1, int offset) {
        if (spr.flag.get(14)) {
            spr.programCounter = offset;
        }
    }

    public void BC(short R1, int offset) {
        if (spr.flag.get(15)) {
            spr.programCounter = offset;
        }
    }

    public void BS(short R1, int offset) {
        if (spr.flag.get(13)) {
            spr.programCounter = offset;
        }
    }

    public void JUMP(short R1, int offset) {
        spr.programCounter = offset;
    }

    public void CALL(short R1, int offset) {
        //while doing errors, check that pc is on stack only
        if (offset >= 65487 && offset <= 65485) {
            spr.programCounter = offset;
        }
    }

    public void ACTION(short R1, int offset) {
        //dont understand what this instruction does
    }

    public void loadWord(short R1, int offset) {
        gpr.generalPurposeRegisters[R1] = (short) (((mem.mainMemory[offset] & 0xFF) << 8) | (mem.mainMemory[offset + 1] & 0xFF));
    }

    public void storeWord(short R1, int offset) {
        short val = gpr.generalPurposeRegisters[R1];
        byte byte1 = (byte) (val & 0x00FF >> 8);
        byte byte2 = (byte) (val * 0x00FF);
        mem.mainMemory[offset] = byte1;
        mem.mainMemory[offset + 1] = byte2;
    }

    public void SHL(short R1) {
        gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] << 1);
        if (gpr.generalPurposeRegisters[R1] == 0) {
            spr.flag.set(14, true);
        }
        spr.flag.set(13, gpr.generalPurposeRegisters[R1] & 0xFF00);
    }

    public void SHR(short R1) {
        gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] >> 1);
        if (gpr.generalPurposeRegisters[R1] == 0) {
            spr.flag.set(14, true);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
            spr.flag.set(13, true);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
            spr.flag.set(13, false);
        }
    }

    public void RTL(short R1) {
        spr.flag.set(15, gpr.generalPurposeRegisters[R1] & 0xFF00);
        SHL(gpr.generalPurposeRegisters[R1]);
        gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] | (spr.flag.get(15) ? 1 : 0));
        if (gpr.generalPurposeRegisters[R1] == 0) {
            spr.flag.set(14, 1);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
            spr.flag.set(13, true);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
            spr.flag.set(13, false);
        }
    }

    public void RTR(short R1) {
        gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] >> 15 | gpr.generalPurposeRegisters[R1] << Short.SIZE - 15);
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 1) {
            spr.flag.set(13, true);
        }
        if ((gpr.generalPurposeRegisters[R1] & 0xFF00) == 0) {
            spr.flag.set(13, false);
        }
    }

    public void INC(short R1) {
        gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] + 1);
    }

    public void DEC(short R1) {
        gpr.generalPurposeRegisters[R1] = (short) (gpr.generalPurposeRegisters[R1] - 1);
    }

    public void PUSH(short R1) {
        short val = gpr.generalPurposeRegisters[R1];
        mem.mainMemory[spr.stackCounter] = (byte) (val & 0x00FF >> 8);
        spr.stackCounter--;
        mem.mainMemory[spr.stackCounter] = (byte) (val * 0x00FF);
        spr.stackCounter--;
    }

    public short POP(short R1) {
        spr.stackCounter++;
        byte val1 = mem.mainMemory[spr.stackCounter];
        spr.stackCounter++;
        byte val2 = mem.mainMemory[spr.stackCounter];
        short val = (short) (((val2 & 0xFF) << 8) | (val1 & 0xFF));
        return val;
    }

    public int RETURN() {
        return POP((short) spr.programCounter);
    }

    public void NOOP() {
        return;
    }

    public void END() {
        //implemented in execute
    }

}
