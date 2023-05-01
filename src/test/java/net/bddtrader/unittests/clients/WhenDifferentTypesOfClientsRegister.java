package net.bddtrader.unittests.clients;

import net.bddtrader.clients.Client;
import net.bddtrader.clients.ClientController;
import net.bddtrader.clients.ClientDirectory;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.portfolios.PortfolioController;
import net.bddtrader.portfolios.PortfolioDirectory;
import net.bddtrader.tradingdata.TradingData;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static net.bddtrader.config.TradingDataSource.DEV;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SerenityJUnit5Extension.class)
public class WhenDifferentTypesOfClientsRegister {

    ClientDirectory clientDirectory = new ClientDirectory();
    PortfolioDirectory portfolioDirectory = new PortfolioDirectory();
    PortfolioController portfolioController = new PortfolioController(TradingDataSource.DEV, portfolioDirectory);
    ClientController controller = new ClientController(clientDirectory, portfolioController);


    public static Stream<Arguments> aClientRegisters() {
        return Stream.of(
                Arguments.of("Sarah-Jane", "Smith"),
                Arguments.of("Bill", "Oddie"),
                Arguments.of("Tim", "Brooke-Taylor")
        );
    }



    @BeforeEach
    public void resetTestData() {
        TradingData.instanceFor(DEV).reset();
    }

    @ParameterizedTest(name = "{index} for {0} {1}")
    @MethodSource
    public void aClientRegisters(String firstName, String lastName) {

        // WHEN
        Client registeredClient = controller.register(Client.withFirstName(firstName).andLastName(lastName).andEmail("sarah-jane@smith.com"));

        // THEN
        assertThat(registeredClient).isEqualToComparingFieldByField(registeredClient);
    }


}
