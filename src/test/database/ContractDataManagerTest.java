package test.database;

import excelchaos_model.database.Contract;
import excelchaos_model.database.ContractDataManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public class ContractDataManagerTest {

    @Test
    void testRemoveAll(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        assertEquals(manager.getAllContracts().size(),0);
    }

    @Test
    void testGetValid(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        BigDecimal num = new BigDecimal("3.5");
        var contract = new Contract(12, "E13", "2", calendar.getTime(), calendar.getTime(),
                num, num, num, "0", false);
        manager.addContract(contract);
        Contract recContract = manager.getContract(12);
        assertEquals(contract.getId(), recContract.getId());
        assertEquals(contract.getBonus_cost(), recContract.getBonus_cost());
        assertEquals(contract.getRegular_cost(), recContract.getRegular_cost());
        assertEquals(contract.getPaygrade(), recContract.getPaygrade());
        assertEquals(contract.getPaylevel(), recContract.getPaylevel());
        assertEquals(contract.getStart_date(), recContract.getStart_date());
        assertEquals(contract.getEnd_date(), recContract.getEnd_date());
        assertEquals(contract.getScope(), recContract.getScope());
        assertEquals(contract.getVbl_status(), recContract.getVbl_status());
        assertEquals(contract.getShk_hourly_rate(), recContract.getShk_hourly_rate());
    }

    @Test
    void testGetInvalid(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        BigDecimal num = new BigDecimal("3.5");
        var contract = new Contract(12, "E13", "2", calendar.getTime(), calendar.getTime(),
                num, num, num, "0", false);
        manager.addContract(contract);
        Contract recContract = manager.getContract(13);
        assertNull(recContract);
    }

    @Test
    void testRemoveValid(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        BigDecimal num = new BigDecimal("3.5");
        var contract = new Contract(12, "E13", "2", calendar.getTime(), calendar.getTime(),
                num, num, num, "0", false);
        manager.addContract(contract);
        manager.removeContract(12);
        Contract recContract = manager.getContract(12);
        assertNull(recContract);
    }

    @Test
    void testRemoveInvalid(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        BigDecimal num = new BigDecimal("3.5");
        var contract = new Contract(12, "E13", "2", calendar.getTime(), calendar.getTime(),
                num, num, num, "0", false);
        manager.addContract(contract);
        manager.removeContract(13);
        assertEquals(manager.getAllContracts().size(), 1);
        assertNotNull(manager.getContract(12));
    }

    @Test
    void testGetAll(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var contracts = new Contract[10];
        for (int i = 0; i < 10; i++){
            BigDecimal num = new BigDecimal("3.5");
            var contract = new Contract(i, "E13", "2", calendar.getTime(), calendar.getTime(),
                    num, num, num, "0", false);
            manager.addContract(contract);
            contracts[i] = contract;
        }
        List<Contract> recContracts = manager.getAllContracts();
        for (int i = 0; i < 10; i++){
            assertEquals(contracts[i].getId(), recContracts.get(i).getId());
            assertEquals(contracts[i].getBonus_cost(), recContracts.get(i).getBonus_cost());
            assertEquals(contracts[i].getRegular_cost(), recContracts.get(i).getRegular_cost());
            assertEquals(contracts[i].getPaygrade(), recContracts.get(i).getPaygrade());
            assertEquals(contracts[i].getPaylevel(), recContracts.get(i).getPaylevel());
            assertEquals(contracts[i].getStart_date(), recContracts.get(i).getStart_date());
            assertEquals(contracts[i].getEnd_date(), recContracts.get(i).getEnd_date());
            assertEquals(contracts[i].getScope(), recContracts.get(i).getScope());
            assertEquals(contracts[i].getVbl_status(), recContracts.get(i).getVbl_status());
            assertEquals(contracts[i].getShk_hourly_rate(), recContracts.get(i).getShk_hourly_rate());
        }
    }

    @Test
    void testGetRowCoun(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        for (int i = 0; i < 10; i++){
            BigDecimal num = new BigDecimal("3.5");
            var contract = new Contract(i, "E13", "2", calendar.getTime(), calendar.getTime(),
                    num, num, num, "0", false);
            manager.addContract(contract);
        }
        assertEquals(manager.getRowCount(), 10);
    }

    @Test
    void testGetRowCountEmpty(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        assertEquals(manager.getRowCount(), 0);
    }

    @Test
    void testUpdateValid(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        BigDecimal num = new BigDecimal("3.5");
        var contract = new Contract(12, "E13", "2", calendar.getTime(), calendar.getTime(),
                num, num, num, "0", false);
        manager.addContract(contract);
        num = new BigDecimal("3.5");
        var contractUpdate = new Contract(12, "E14", "2", calendar.getTime(), calendar.getTime(),
                num, num, num, "0", false);
        manager.updateContract(contractUpdate);
        Contract recContract = manager.getContract(12);
        assertEquals(contractUpdate.getId(), recContract.getId());
        assertEquals(contractUpdate.getBonus_cost(), recContract.getBonus_cost());
        assertEquals(contractUpdate.getRegular_cost(), recContract.getRegular_cost());
        assertEquals(contractUpdate.getPaygrade(), recContract.getPaygrade());
        assertEquals(contractUpdate.getPaylevel(), recContract.getPaylevel());
        assertEquals(contractUpdate.getStart_date(), recContract.getStart_date());
        assertEquals(contractUpdate.getEnd_date(), recContract.getEnd_date());
        assertEquals(contractUpdate.getScope(), recContract.getScope());
        assertEquals(contractUpdate.getVbl_status(), recContract.getVbl_status());
        assertEquals(contractUpdate.getShk_hourly_rate(), recContract.getShk_hourly_rate());
    }

    @Test
    void testUpdateInvalid(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        BigDecimal num = new BigDecimal("3.5");
        var contract = new Contract(12, "E13", "2", calendar.getTime(), calendar.getTime(),
                num, num, num, "0", false);
        manager.addContract(contract);
        num = new BigDecimal("3.5");
        var contractUpdate = new Contract(14, "E14", "2", calendar.getTime(), calendar.getTime(),
                num, num, num, "0", false);
        manager.updateContract(contractUpdate);
        manager.updateContract(contractUpdate);
        Contract recContract = manager.getContract(12);
        assertEquals(contract.getId(), recContract.getId());
        assertEquals(contract.getBonus_cost(), recContract.getBonus_cost());
        assertEquals(contract.getRegular_cost(), recContract.getRegular_cost());
        assertEquals(contract.getPaygrade(), recContract.getPaygrade());
        assertEquals(contract.getPaylevel(), recContract.getPaylevel());
        assertEquals(contract.getStart_date(), recContract.getStart_date());
        assertEquals(contract.getEnd_date(), recContract.getEnd_date());
        assertEquals(contract.getScope(), recContract.getScope());
        assertEquals(contract.getVbl_status(), recContract.getVbl_status());
        assertEquals(contract.getShk_hourly_rate(), recContract.getShk_hourly_rate());
        assertNull(manager.getContract(13));
    }
}
