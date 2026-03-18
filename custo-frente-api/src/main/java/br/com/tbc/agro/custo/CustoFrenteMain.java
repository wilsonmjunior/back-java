package br.com.tbc.agro.custo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"br.com.tbc.agro.core", "br.com.tbc.agro.custo"})
@EntityScan({"br.com.tbc.agro.core"})
@EnableJpaRepositories({"br.com.tbc.agro.core"})
public class CustoFrenteMain {

    public static void main(final String[] args) {
        SpringApplication.run(CustoFrenteMain.class, args);
    }
}
