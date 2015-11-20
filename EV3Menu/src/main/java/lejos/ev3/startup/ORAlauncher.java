package lejos.ev3.startup;

import java.io.File;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.Image;
import lejos.utility.Delay;

/**
 * Executes the Open Roberta lab user program after it is downloaded.
 *
 * @author dpyka
 */
public class ORAlauncher {

    private static final String CMD_ORA_RUN = "jrun -jar ";
    private static final String PROGRAMS_DIRECTORY = "/home/lejos/programs";

    private static final String OpenRobertaLabLogo =
        "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00c0\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0040\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0040\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00c0\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00a0\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00a0\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00a0\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00a0\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00b0\u0010\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00c0\u00ff\u0000\u0000\u00b0\u000f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00f8\u00ff\u007f\u0000\u0030\u000c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00e0\u00ff\u00ff\u0000\u0030\u000c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u00ff\u0001\u0010\u0004\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00ff\u0000\u0010\u0006\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0018\u0000\u0000\u0000\u0000\u0000\u0070\u0000\u0010\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0000\u003e\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0010\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0003\u0080\u002f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0008\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0002\u00e0\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0008\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0002\u00f0\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0088\u000c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0047\u0002\u007c\u0000\u0000\u0000\u0000\u00f0\u007f\u0000\u0000\u0008\u000f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u007f\u0002\u007c\u0000\u0000\u0000\u0000\u00fc\u00ff\u0000\u0000\u0008\u0006\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0070\u0002\u001f\u0000\u00f8\u0001\u0000\u000f\u0080\u0003\u0000\u0008\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0001\u0002\u001f\u0000\u00ff\u000f\u0080\u0007\u0000\u000f\u00e0\u0089\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u000e\u00e0\u0007\u007e\u00c0\u0001\u0000\u00fc\u00ff\u00c9\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u000c\u00e0\u0000\u00f0\u00e0\u0000\u0000\u00f8\u00ff\u0069\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0006\u0002\u0000\u0038\u0000\u0080\u0033\u0000\u0000\u00f0\u00ff\u0031\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0006\u0002\u0000\u001c\u0000\u0080\u0037\u0000\u0000\u00f0\u00ff\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0008\u0002\u0000\u0006\u0000\u0000\u001e\u0000\u0000\u00c0\u00ff\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0018\u0002\u0000\u0007\u0000\u0000\u001c\u0000\u0000\u00c0\u00ff\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0011\u0002\u0080\u0001\u0000\u0000\u000c\u0000\u0000\u00c0\u00ff\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u002e\u0002\u00c0\u0001\u0000\u0000\u000c\u0000\u001e\u0080\u00ff\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u003c\u0002\u00c0\u0000\u0000\u0000\u000c\u0000\u003f\u0080\u00ff\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u0001\u0002\u0060\u0000\u0000\u0000\u0006\u0080\u0043\u0000\u00ff\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0060\u0000\u0000\u0000\u0006\u0080\u0043\u0000\u00ff\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0002\u0002\u0060\u0000\u0080\u001f\u0006\u00c0\u00c3\u0000\u00ff\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0002\u0002\u0060\u0000\u00c0\u003f\u0006\u00c0\u00e7\u0000\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0004\u0002\u003f\u0000\u00e0\u0061\u0006\u00c0\u00ff\u0000\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u000c\u00c2\u003f\u0000\u00e0\u0061\u0006\u0080\u007f\u0000\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0018\u00fe\u003f\u0000\u00f0\u0061\u000c\u0080\u007f\u0080\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0018\u00fe\u003f\u0000\u00f0\u0063\u000c\u0000\u003f\u0080\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0030\u00fe\u003f\u0000\u00f0\u007f\u000c\u0000\u0000\u00c0\u0001\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00e0\u00ff\u003f\u0000\u00e0\u007f\u0018\u0000\u0000\u00c0\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u00fd\u003f\u0000\u00c0\u003f\u0018\u0000\u0000\u00c0\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00fc\u007f\u0000\u0080\u001f\u0030\u0000\u0000\u0070\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00f8\u007f\u0000\u0000\u0007\u0030\u0000\u0000\u0030\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00f8\u007f\u0000\u0000\u0000\u00e0\u0000\u0000\u0038\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00f8\u007f\u0000\u0000\u0000\u00e0\u0001\u0000\u001c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00f0\u00ff\u0000\u0000\u0000\u00f0\u0007\u0000\u000f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00f0\u00ff\u0001\u0000\u0000\u00f0\u000f\u0080\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00f0\u008f\u0001\u0000\u0000\u00f8\u00ff\u00ff\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00f0\u0081\u0003\u0000\u0000\u00f8\u00ff\u007f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0006\u0000\u0000\u00fe\u001f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u001c\u0000\u0080\u00ff\u001f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0038\u0000\u0080\u00ff\u001f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00e0\u0000\u00f0\u00ff\u000f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00e0\u0007\u007e\u00ff\u000f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00ff\u008f\u00ff\u000f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00f8\u0081\u00ff\u000f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u00ff\u000f\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0080\u00ff\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00c0\u00ff\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00c0\u00ff\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00e0\u00ff\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00e0\u00ff\u0003\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00f0\u00ff\u00ff\u00ff\u007f\u00f8\u00ff\u00ff\u00ff\u003f\u00fc\u00ff\u00ff\u00ff\u001f\u00fe\u00ff\u00ff\u00ff\u0007\u0000\u0000\u0000\u00f8\u00ff\u00ff\u00ff\u00ff\u00fc\u00ff\u00ff\u00ff\u007f\u00fe\u00ff\u00ff\u00ff\u003f\u00ff\u00ff\u00ff\u00ff\u000f\u0000\u0000\u0000\u00f8\u00ff\u00ff\u00ff\u00ff\u00fc\u00ff\u00ff\u00ff\u007f\u00fe\u00ff\u00ff\u00ff\u003f\u00ff\u00ff\u00ff\u00ff\u000f\u0000\u0000\u0000\u00f8\u00ff\u00ff\u00ff\u00ff\u00f8\u00ff\u00ff\u00ff\u007f\u00fe\u00ff\u00ff\u00ff\u003f\u00fe\u00ff\u00ff\u00ff\u000f\u0000\u0000\u0000\u00f8\u00ff\u00ff\u00ff\u00ff\u00f3\u00ff\u00ff\u00ff\u007f\u00fe\u00ff\u00ff\u00ff\u00ff\u00fc\u00ff\u00ff\u00ff\u003f\u0000\u0000\u0000\u00f8\u0001\u00fc\u0007\u00fc\u00e7\u0007\u0000\u00c0\u007f\u00fe\u0001\u0080\u00ff\u00ff\u00f9\u003f\u00c0\u00ff\u007f\u0000\u0000\u0000\u00f8\u0001\u00f8\u0007\u00fc\u00e7\u0007\u0000\u00c0\u007f\u00fe\u0001\u0000\u00fe\u00ff\u00f9\u0007\u0000\u00ff\u007f\u0000\u0000\u0000\u00f8\u0001\u00f8\u0007\u00fc\u00e7\u0007\u0000\u00c0\u007f\u00fe\u0001\u0000\u00f8\u00ff\u00f9\u0003\u0000\u00fc\u007f\u0000\u0000\u0000\u00f8\u0001\u00f0\u0007\u00fc\u00e7\u0007\u0000\u00c0\u007f\u00fe\u0001\u0000\u00f0\u00ff\u00f9\u0001\u0000\u00f8\u007f\u0000\u0000\u0000\u00f8\u0001\u00f0\u0007\u00fc\u00f7\u0007\u0000\u00c0\u007f\u00fe\u0001\u0000\u00f0\u00ff\u00fd\u0000\u0000\u00f0\u007f\u0000\u0000\u0000\u00f8\u0001\u00e0\u0007\u00fc\u00f3\u0007\u00fc\u00ff\u007f\u00fe\u0001\u001f\u00e0\u00ff\u007c\u0080\u001f\u00e0\u003f\u0000\u0000\u0000\u00f8\u0001\u00e0\u0007\u00fc\u00f8\u0007\u00fc\u00ff\u007f\u00fe\u0001\u003f\u00e0\u003f\u007e\u00c0\u003f\u00e0\u000f\u0000\u0000\u0000\u00f8\u0001\u00c0\u0007\u00fc\u00fc\u0007\u00fc\u00ff\u007f\u00fe\u0001\u007f\u00e0\u003f\u003f\u00e0\u007f\u00c0\u000f\u0000\u0000\u0000\u00f8\u0001\u00c0\u0007\u00fc\u00fc\u0007\u00fc\u00ff\u007f\u00fe\u0001\u007f\u00e0\u003f\u003f\u00e0\u007f\u00c0\u000f\u0000\u0000\u0000\u00f8\u0001\u0080\u0007\u00fc\u00fc\u0007\u00fc\u00ff\u007f\u00fe\u0001\u007f\u00e0\u003f\u001f\u00e0\u007f\u0080\u000f\u0000\u0000\u0000\u00f8\u0001\u0081\u0007\u00fc\u00fc\u0007\u00fc\u00ff\u007f\u00fe\u0001\u003f\u00e0\u003f\u001f\u00f0\u00ff\u0080\u000f\u0000\u0000\u0000\u00f8\u0001\u0001\u0007\u00fc\u00fc\u0007\u0000\u00e0\u007f\u00fe\u0001\u001f\u00e0\u003f\u001f\u00f0\u00ff\u0080\u000f\u0000\u0000\u0000\u00f8\u0001\u0003\u0007\u00fc\u00fc\u0007\u0000\u00e0\u007f\u00fe\u0001\u0000\u00e0\u003f\u001f\u00f0\u00ff\u0080\u000f\u0000\u0000\u0000\u00f8\u0001\u0003\u0006\u00fc\u00fc\u0007\u0000\u00e0\u007f\u00fe\u0001\u0000\u00f0\u003f\u001f\u00f0\u00ff\u0080\u000f\u0000\u0000\u0000\u00f8\u0001\u0007\u0006\u00fc\u00fc\u0007\u0000\u00e0\u007f\u00fe\u0001\u0000\u00f8\u003f\u001f\u00f0\u00ff\u0080\u000f\u0000\u0000\u0000\u00f8\u0001\u0007\u0004\u00fc\u00fc\u0007\u0000\u00e0\u007f\u00fe\u0001\u0000\u00fc\u003f\u001f\u00f0\u00ff\u0080\u000f\u0000\u0000\u0000\u00f8\u0001\u000f\u0000\u00fc\u00fc\u0007\u00fc\u00ff\u007f\u00fe\u0001\u0000\u00ff\u003f\u001f\u00f0\u00ff\u0080\u000f\u0000\u0000\u0000\u00f8\u0001\u000f\u0000\u00fc\u00fc\u0007\u00fc\u00ff\u007f\u00fe\u0001\u00c0\u00ff\u003f\u001f\u00e0\u007f\u00c0\u000f\u0000\u0000\u0000\u00f8\u0001\u001f\u0000\u00fc\u00fc\u0007\u00fc\u00ff\u007f\u00fe\u0001\u00ff\u00ff\u003f\u003f\u00e0\u007f\u00c0\u000f\u0000\u0000\u0000\u00f8\u0001\u001f\u0000\u00fc\u00fc\u0007\u00fc\u00ff\u007f\u00fe\u0001\u00ff\u00ff\u003f\u003f\u00e0\u007f\u00c0\u000f\u0000\u0000\u0000\u00f8\u0001\u003f\u0000\u00fc\u00fc\u0007\u00fc\u00ff\u007f\u00fc\u0001\u00ff\u00ff\u003f\u003f\u00c0\u003f\u00e0\u000f\u0000\u0000\u0000\u00f8\u0001\u003f\u0000\u00fc\u00fc\u0007\u00fc\u00ff\u00ff\u00f9\u0001\u00ff\u00ff\u003f\u007f\u0080\u001f\u00e0\u000f\u0000\u0000\u0000\u00f8\u0001\u007f\u0000\u00fc\u00fc\u0007\u0000\u00c0\u00ff\u00f3\u0001\u00ff\u00ff\u003f\u007f\u0000\u0000\u00f0\u000f\u0000\u0000\u0000\u00f8\u0001\u007f\u0000\u00fc\u00fc\u0007\u0000\u00c0\u00ff\u00f3\u0001\u00ff\u00ff\u003f\u00ff\u0000\u0000\u00f8\u000f\u0000\u0000\u0000\u00f8\u0001\u00ff\u0000\u00fc\u00fc\u0007\u0000\u00c0\u00ff\u00f3\u0001\u00ff\u00ff\u003f\u00ff\u0003\u0000\u00fc\u000f\u0000\u0000\u0000\u00f8\u0001\u00ff\u0000\u00fc\u00fc\u0007\u0000\u00c0\u00ff\u00f3\u0001\u00ff\u00ff\u003f\u00ff\u0007\u0000\u00fe\u000f\u0000\u0000\u0000\u00f8\u0001\u00ff\u0001\u00fc\u00fc\u0007\u0000\u00c0\u00ff\u00fb\u0001\u00ff\u00ff\u003f\u00ff\u001f\u0080\u00ff\u000f\u0000\u0000\u0000\u00f8\u00ff\u00ff\u00ff\u00ff\u00fc\u00ff\u00ff\u00ff\u00ff\u00f9\u00ff\u00ff\u00ff\u003f\u00ff\u00ff\u00ff\u00ff\u000f\u0000\u0000\u0000\u00f8\u00ff\u00ff\u00ff\u00ff\u00fc\u00ff\u00ff\u00ff\u007f\u00fc\u00ff\u00ff\u00ff\u003f\u00ff\u00ff\u00ff\u00ff\u000f\u0000\u0000\u0000\u00f8\u00ff\u00ff\u00ff\u00ff\u00fc\u00ff\u00ff\u00ff\u007f\u00fe\u00ff\u00ff\u00ff\u003f\u00ff\u00ff\u00ff\u00ff\u000f\u0000\u0000\u0000\u00f8\u00ff\u00ff\u00ff\u00ff\u00fc\u00ff\u00ff\u00ff\u007f\u00fe\u00ff\u00ff\u00ff\u003f\u00ff\u00ff\u00ff\u00ff\u000f\u0000\u0000\u0000\u00f0\u00ff\u00ff\u00ff\u007f\u00f8\u00ff\u00ff\u00ff\u003f\u00fc\u00ff\u00ff\u00ff\u001f\u00fe\u00ff\u00ff\u00ff\u0007\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00fe\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u00fe\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u007c\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";

    private static final Image image = new Image(178, 128, Utils.stringToBytes8(OpenRobertaLabLogo));

    private static final GraphicsLCD glcd = LocalEV3.get().getGraphicsLCD();

    private static boolean isRunning = false;

    public static void setRunning(boolean bool) {
        isRunning = bool;
    }

    public static boolean isRunning() {
        return isRunning;
    }

    /**
     * Executes the user's program as a new java process.
     *
     * @param programName
     *        Filename without directory, for example Linefollower.jar.
     */
    public static void runProgram(String programName) {
        File robertalabFile = new File(ORAlauncher.PROGRAMS_DIRECTORY, programName);
        exec(CMD_ORA_RUN + robertalabFile.getPath() + " true", ORAlauncher.PROGRAMS_DIRECTORY);
    }

    /**
     * Method is based on leJOS program execution. Modified for Open Roberta
     * Lab. No wrapper for sysout and syserr, no stacktrace displaying.
     *
     * @param command
     *        The shell commands to launch the new process.
     * @param directory
     *        The programs where all user programs are saved.
     */
    private static void exec(String command, String directory) {
        int displaystate = GraphicStartup.menu.ind.displayState;
        Process program = null;
        GraphicStartup.menu.suspend();
        try {
            setRunning(true);

            glcd.drawImage(image, 0, 0, 0);
            glcd.refresh();

            program = new ProcessBuilder(command.split(" ")).directory(new File(directory)).start();

            while ( true ) {
                int b = Button.getButtons();
                if ( b == 6 ) {
                    System.out.println("Killing the process");
                    program.destroy();
                    GraphicStartup.resetMotors();
                }
                try {
                    System.out.println("ORA process exitvalue: " + program.exitValue());
                    break;
                } catch ( IllegalThreadStateException e ) {
                    // go on
                }
                Delay.msDelay(200);
            }
            program.waitFor();
        } catch ( Exception e ) {
            System.err.println("Failed to execute ORA program: " + e.getMessage());
        } finally {
            Button.LEDPattern(0);
            program = null;
            setRunning(false);
            Delay.msDelay(500);
            GraphicStartup.menu.ind.setDisplayState(displaystate);
            GraphicStartup.menu.resume();
            GraphicStartup.menu.suspend(); // debug screen
            GraphicStartup.menu.resume();
        }
    }

}
