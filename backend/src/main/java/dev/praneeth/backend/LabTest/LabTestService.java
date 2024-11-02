package dev.praneeth.backend.LabTest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabTestService {

    private final LabTestDao labTestDao;

    public LabTestService(LabTestDao labTestDao) {
        this.labTestDao = labTestDao;
    }

    public List<LabTest> getAllLabTests() {
        return labTestDao.getAllLabTests();
    }

    public void addLabTest(LabTest labTest) {
        labTestDao.addLabTest(labTest);
    }

    public void deleteLabTest(Integer labTestId) {
        labTestDao.getLabTestById(labTestId)
                .orElseThrow(() -> new IllegalStateException("LabTest with id " + labTestId + " does not exist"));
        labTestDao.deleteLabTest(labTestId);
    }

    @Transactional
    public void updateLabTest(Integer labTestId, LabTestUpdateRequest updateRequest) {
        LabTest labTest = labTestDao.getLabTestById(labTestId)
                .orElseThrow(() -> new IllegalStateException("LabTest with id " + labTestId + " does not exist"));

        if (updateRequest.getName() != null) {
            labTest.setName(updateRequest.getName());
        }
        if (updateRequest.getDescription() != null) {
            labTest.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getNormalRange() != null) {
            labTest.setNormalRange(updateRequest.getNormalRange());
        }
        if (updateRequest.getUnits() != null) {
            labTest.setUnits(updateRequest.getUnits());
        }

        labTestDao.updateLabTest(labTest);
    }
}
