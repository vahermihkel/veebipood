package ee.mihkel.veebipood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VeebipoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(VeebipoodApplication.class, args);
    }

}

// 1. T 04.08 - Controller, Entity, Calculation
// 2. E 10.08 - Andmebaas, Repository, Veateated
// 3. K 12.08 - Hibernate (seosed), Service, Unit Test
// 4. T 18.08 - Frontend  18.30-20.45   19.30-19.45
// 5. K 19.09 - Frontend jätkub
// 6. E 24.08 - login, signup (ilma backend autentimiseta), tellimused
// 7. N 27.08 - Angular: Subject, Services
// 8. T 01.09 - Pagination, API päringud: tarnija tooted
// 9. N 03.09 - pakiautomaadid, makse
//10. T 08.09 - makse jätk. CRON, logide ülevaade
//11. N 10.09 - Docker. Serverisse (Render), email
//12. T 15.09 - kubernetes
//13. N 17.09 - WebSocket. Beanide ülevaade, cache
//14. T 22.09 - autentimine
//15. K 23.09 - autentimine
//16. T 29.09
//17. N 01.10???
//18. 07.10/22.10 2ak/h 17.30-19.00


// SmartId?
// Andmebaasi ajatemplid
// Andmebaasi auditid
//