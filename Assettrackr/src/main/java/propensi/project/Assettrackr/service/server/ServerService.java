package propensi.project.Assettrackr.service.server;

import propensi.project.Assettrackr.model.Server;
import propensi.project.Assettrackr.model.dto.CreateUpdateServerRequest;

import java.util.List;

public interface ServerService {

    public Server createServer(CreateUpdateServerRequest request) throws RuntimeException;

}