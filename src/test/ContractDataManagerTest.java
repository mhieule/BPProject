import excelchaos_model.Contract;
import excelchaos_model.ContractDataManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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
        var contract = new Contract(12, "E13", "2", "01.01.2022", "10.10.2023",
                50.20, 17.10);
        manager.addContract(contract);
        Contract recContract = manager.getContract(12);
        assertEquals(contract.getId(), recContract.getId());
        assertEquals(contract.getBonus_cost(), recContract.getBonus_cost());
        assertEquals(contract.getRegular_cost(), recContract.getRegular_cost());
        assertEquals(contract.getPaygrade(), recContract.getPaygrade());
        assertEquals(contract.getPaylevel(), recContract.getPaylevel());
        assertEquals(contract.getStart_date(), recContract.getStart_date());
        assertEquals(contract.getEnd_date(), recContract.getEnd_date());
    }

    @Test
    void testGetInvalid(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        var contract = new Contract(12, "E13", "2", "01.01.2022", "10.10.2023",
                50.20, 17.10);
        manager.addContract(contract);
        Contract recContract = manager.getContract(13);
        assertNull(recContract);
    }

    @Test
    void testRemoveValid(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        var contract = new Contract(12, "E13", "2", "01.01.2022", "10.10.2023",
                50.20, 17.10);
        manager.addContract(contract);
        manager.removeContract(12);
        Contract recContract = manager.getContract(12);
        assertNull(recContract);
    }

    @Test
    void testRemoveInvalid(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        var contract = new Contract(12, "E13", "2", "01.01.2022", "10.10.2023",
                50.20, 17.10);
        manager.addContract(contract);
        manager.removeContract(13);
        assertEquals(manager.getAllContracts().size(), 1);
        assertNotNull(manager.getContract(12));
    }

    @Test
    void testGetAll(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        var contracts = new Contract[10];
        for (int i = 0; i < 10; i++){
            var contract = new Contract(i, "E13", "2", "01.01.2022", "10.10.2023",
                    50.20, 17.10);
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
        }
    }

    @Test
    void testGetRowCoun(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        for (int i = 0; i < 10; i++){
            var contract = new Contract(i, "E13", "2", "01.01.2022", "10.10.2023",
                    50.20, 17.10);
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
        var contract = new Contract(12, "E13", "2", "01.01.2022", "10.10.2023",
                50.20, 17.10);
        manager.addContract(contract);
        var contractUpdate = new Contract(12, "E14", "2", "01.01.2022", "10.10.2023",
                50.20, 17.10);
        manager.updateContract(contractUpdate);
        Contract recContract = manager.getContract(12);
        assertEquals(contractUpdate.getId(), recContract.getId());
        assertEquals(contractUpdate.getBonus_cost(), recContract.getBonus_cost());
        assertEquals(contractUpdate.getRegular_cost(), recContract.getRegular_cost());
        assertEquals(contractUpdate.getPaygrade(), recContract.getPaygrade());
        assertEquals(contractUpdate.getPaylevel(), recContract.getPaylevel());
        assertEquals(contractUpdate.getStart_date(), recContract.getStart_date());
        assertEquals(contractUpdate.getEnd_date(), recContract.getEnd_date());
    }

    @Test
    void testUpdateInvalid(){
        var manager = new ContractDataManager();
        manager.removeAllContracts();
        var contract = new Contract(12, "E13", "2", "01.01.2022", "10.10.2023",
                50.20, 17.10);
        manager.addContract(contract);
        var contractUpdate = new Contract(13, "E14", "2", "01.01.2022", "10.10.2023",
                50.20, 17.10);
        manager.updateContract(contractUpdate);
        Contract recContract = manager.getContract(12);
        assertEquals(contract.getId(), recContract.getId());
        assertEquals(contract.getBonus_cost(), recContract.getBonus_cost());
        assertEquals(contract.getRegular_cost(), recContract.getRegular_cost());
        assertEquals(contract.getPaygrade(), recContract.getPaygrade());
        assertEquals(contract.getPaylevel(), recContract.getPaylevel());
        assertEquals(contract.getStart_date(), recContract.getStart_date());
        assertEquals(contract.getEnd_date(), recContract.getEnd_date());
        assertNull(manager.getContract(13));
    }
}
