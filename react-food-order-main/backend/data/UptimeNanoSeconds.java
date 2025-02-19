public class UptimeNanoSeconds {
    public static void main(String[] args) {
        for(int i = 0 ; i<=10;i++){


            long systemStartTime = System.currentTimeMillis() - System.nanoTime() / 1000000;
        long currentSystemTime = System.currentTimeMillis();

        // Sistem başlangıcından beri geçen süre nanosaniye cinsinden
        long uptimeNanoSeconds = (currentSystemTime - systemStartTime) * 1000000;

        System.out.println("Sistem başlangıcından beri geçen süre (nanosaniye): " + uptimeNanoSeconds);
        try {
            // 1000 milisaniye (1 saniye) bekleme süresi
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // InterruptedException durumuyla başa çıkma
            e.printStackTrace();
            // İstisna yakalandığında gerekli işlemleri buraya ekleyebilirsiniz
        }

        }
        
    }
}