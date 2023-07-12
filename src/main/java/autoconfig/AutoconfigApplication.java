package autoconfig;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AutoconfigApplication {

	/**
	 *  <Core 자동구성>
	 *     가장 기본으로 등록되어 있는 자동 구성요소 조회해 보기.
	 *          = 14개...
	 *
	 *  <Web 자동구성>
	 *      'org.springframework.boot:spring-boot-starter-web' 추가 시
	 *          = 63-14 = 49개...
     *
	 *  <Jdbc 자동구성>
	 *      'org.springframework.boot:spring-boot-starter-jdbc' 추가 시
	 *          = 33-14 = 19개...
	 * */
	@Bean
	ApplicationRunner run(ConditionEvaluationReport report) {
		return args -> {
			System.out.println( " [자동구성정보] 사용 갯수 = " +
					report.getConditionAndOutcomesBySource()
							.entrySet()
							.stream()
							.filter(co -> co.getValue().isFullMatch())
							.filter(co -> co.getKey().indexOf("Jmx")<0)  // jmx 와 관련된 것은 제외하고 가져온다.
							.map(co -> {
								System.out.println("\t" + co.getKey());
								co.getValue().forEach(c->{
									System.out.println("\t\t" + c.getOutcome());
								});
								System.out.println();
								return co;
							}).count()
			);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(AutoconfigApplication.class, args);
	}

}
