package com.epam.catgenome.manager.blast;

import com.epam.catgenome.client.blast.BlastApi;
import com.epam.catgenome.client.blast.BlastApiBuilder;
import com.epam.catgenome.exception.BlastResponseException;
import com.epam.catgenome.manager.blast.dto.BlastRequest;
import com.epam.catgenome.manager.blast.dto.BlastRequestInfo;
import com.epam.catgenome.component.MessageHelper;
import com.epam.catgenome.constant.MessagesConstants;
import com.epam.catgenome.manager.blast.dto.BlastRequestResult;
import com.epam.catgenome.exception.BlastRequestException;
import com.epam.catgenome.util.QueryUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class BlastRequestManager {

    private BlastApi blastApi;

    @Value("${blast.server.url}")
    private String blastServer;

    @PostConstruct
    public void init() {
        this.blastApi = new BlastApiBuilder(0, 0, blastServer).buildClient();
    }

    public BlastRequestInfo createTask(final BlastRequest blastRequest) throws BlastRequestException {
        try {
            return QueryUtils.execute(blastApi.createTask(blastRequest)).getPayload();
        } catch (BlastResponseException e) {
            throw new BlastRequestException(MessageHelper.getMessage(MessagesConstants
                    .ERROR_BLAST_REQUEST), e);
        }
    }

    public BlastRequestInfo getTaskStatus(final long id) throws BlastRequestException {
        try {
            return QueryUtils.execute(blastApi.getTask(id)).getPayload();
        } catch (BlastResponseException e) {
            throw new BlastRequestException(MessageHelper.getMessage(MessagesConstants
                    .ERROR_BLAST_REQUEST), e);
        }
    }

    public BlastRequestInfo cancelTask(final long id) throws BlastRequestException {
        try {
            return QueryUtils.execute(blastApi.cancelTask(id)).getPayload();
        } catch (BlastResponseException e) {
            throw new BlastRequestException(MessageHelper.getMessage(MessagesConstants
                    .ERROR_BLAST_REQUEST), e);
        }
    }

    public BlastRequestResult getResult(final long taskId) throws BlastRequestException {
        try {
            return QueryUtils.execute(blastApi.getResult(taskId)).getPayload();
        } catch (BlastResponseException e) {
            throw new BlastRequestException(MessageHelper.getMessage(MessagesConstants
                    .ERROR_BLAST_REQUEST), e);
        }
    }

    public ResponseBody getRawResult(final long taskId) throws BlastRequestException {
        try {
            return QueryUtils.execute(blastApi.getRawResult(taskId));
        } catch (BlastResponseException e) {
            throw new BlastRequestException(MessageHelper.getMessage(MessagesConstants
                    .ERROR_BLAST_REQUEST), e);
        }
    }
}
