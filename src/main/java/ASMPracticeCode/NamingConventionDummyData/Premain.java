package ASMPracticeCode.NamingConventionDummyData;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Premain {
//    public static void premain(String agentArgs, Instrumentation inst) {
//        inst.addTransformer(new ClassFileTransformer() {
//            @Override
//            public byte[] transform(
//                    ClassLoader l,
//                    String name,
//                    Class c,
//                    ProtectionDomain d,
//                    byte[] b)
//                    throws IllegalClassFormatException {
//                if(name.equals("java/lang/Integer")) {
//                    CustomClassWriter cr = null;
//                    try {
//                        cr = new CustomClassWriter();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    return cr.addField();
//                }
//                return b;
//            }
//        });
//    }
}