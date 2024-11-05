package dev.praneeth.backend.LabResult;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LabResultService {

    private final LabResultDao labResultDao;

    public LabResultService(LabResultDao labResultDao) {
        this.labResultDao = labResultDao;
    }

    public List<LabResult> getAllLabResults() {
        return labResultDao.getAllLabResults();
    }

    public Optional<LabResult> getLabResultById(Integer labResultId) {
        return labResultDao.getLabResultById(labResultId);
    }

    public void addLabResult(LabResult labResult) {
        labResultDao.addLabResult(labResult);
    }

    public void deleteLabResult(Integer labResultId) {
        labResultDao.getLabResultById(labResultId)
                .orElseThrow(() -> new IllegalStateException("LabResult with id " + labResultId + " does not exist"));
        labResultDao.deleteLabResult(labResultId);
    }

    @Transactional
    public void updateLabResult(Integer labResultId, LabResultUpdateRequest updateRequest) {
        LabResult labResult = labResultDao.getLabResultById(labResultId)
                .orElseThrow(() -> new IllegalStateException("LabResult with id " + labResultId + " does not exist"));

        if (updateRequest.getResultValue() != null) {
            labResult.setResultValue(updateRequest.getResultValue());
        }
        if (updateRequest.getTestDate() != null) {
            labResult.setTestDate(updateRequest.getTestDate());
        }
        if (updateRequest.getNotes() != null) {
            labResult.setNotes(updateRequest.getNotes());
        }

        labResultDao.updateLabResult(labResult);
    }
}
