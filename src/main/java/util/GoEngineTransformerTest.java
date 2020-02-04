//import com.sun.jna.*;public class Client {
//    public interface Awesome extends Library {
//        public class GoSlice extends Structure {
////      ...
//            public Pointer data;
//            public long len;
//            public long cap;
//        }
//
//        public class GoString extends Structure {
////      ...
//            public String p;
//            public long n;
//        }
//        public long Add(long a, long b);
//        public double Cosine(double val);
//        public void Sort(GoSlice.ByValue vals);
//        public long Log(GoString.ByValue str);
//    }  static public void main(String argv[]) {
//        Awesome awesome = (Awesome) Native.loadLibrary(
//                "./awesome.so", Awesome.class);    System.out.printf(... awesome.Add(12, 99));
//        System.out.printf(... awesome.Cosine(1.0));    long[] nums = new long[]{53,11,5,2,88};
//        Memory arr = new Memory(... Native.getNativeSize(Long.TYPE));
//        Awesome.GoSlice.ByValue slice = new Awesome.GoSlice.ByValue();
//        slice.data = arr;
//        slice.len = nums.length;
//        slice.cap = nums.length;
//        awesome.Sort(slice);    Awesome.GoString.ByValue str = new Awesome.GoString.ByValue();
//        str.p = "Hello Java!";
//        str.n = str.p.length();
//        System.out.printf(... awesome.Log(str));
//    }
//}