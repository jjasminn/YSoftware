import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Accounting{
    double hourlyRate = 120;
    public double calculateSalaryForFullTimeWorker(){
        LocalDate ayBaslangic = LocalDate.now().withDayOfMonth(1); 
        LocalDate aySonu = ayBaslangic.withDayOfMonth(ayBaslangic.lengthOfMonth()); 
        long calismaGunSayisi = ChronoUnit.DAYS.between(ayBaslangic, aySonu) + 1; 
        long haftasonuGunSayisi = calismaGunSayisi / 7 * 2;
        long toplamCalismaSaati = (calismaGunSayisi - haftasonuGunSayisi) * 8; 
        
        return toplamCalismaSaati*hourlyRate;


    }
    public double calculateSalaryForHalfTimeWorker(){
        LocalDate ayBaslangic = LocalDate.now().withDayOfMonth(1); 
        LocalDate aySonu = ayBaslangic.withDayOfMonth(ayBaslangic.lengthOfMonth()); 
        long calismaGunSayisi = ChronoUnit.DAYS.between(ayBaslangic, aySonu) + 1; 
        long haftasonuGunSayisi = calismaGunSayisi / 7 * 2;
        long toplamCalismaSaati = (calismaGunSayisi - haftasonuGunSayisi) * 4; //yari zamanli
        return toplamCalismaSaati*hourlyRate;


    }
    public static double terminationPay(LocalDate startDate,double salart) {
        LocalDate dateNow = LocalDate.now();
        long yearOfWork = ChronoUnit.YEARS.between(startDate,dateNow);

        double termination = salart * yearOfWork;

        return termination;
    }

   
}