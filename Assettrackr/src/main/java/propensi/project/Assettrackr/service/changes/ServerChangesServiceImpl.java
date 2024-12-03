package propensi.project.Assettrackr.service.changes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.project.Assettrackr.model.*;
import propensi.project.Assettrackr.model.dto.request.*;
import propensi.project.Assettrackr.model.dto.response.DeveloperResponse;
import propensi.project.Assettrackr.model.dto.response.FinishedChangesResponse;
import propensi.project.Assettrackr.model.dto.response.ChartResponse;
import propensi.project.Assettrackr.model.dto.response.ServerChangesResponse;
import propensi.project.Assettrackr.model.projection.LineChartView;
import propensi.project.Assettrackr.model.projection.PieChartView;
import propensi.project.Assettrackr.repository.*;
import propensi.project.Assettrackr.security.jwt.JwtUtils;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServerChangesServiceImpl implements ServerChangesService{
    @Autowired
    private ServerChangesRepository repository;

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ServerChangesResponse createServerChanges(CreateChangesRequest request, UserModel user) throws Exception{
        Server server = serverRepository.getReferenceById(request.getServerId());
        ServerChanges response = repository.save(ServerChanges.builder()
                .server(server)
                .tipePerbaikan(request.getTipePerbaikan())
                .detailPerbaikan(request.getDetailPerbaikan())
                .tanggalDibuat(new Date())
                .status(ServerChangesStatus.valueOf(request.getStatus()))
                .anggota(user)
                .developers(new ArrayList<Developer>())
                .build());


        server.getListServerChanges().add(response);
        serverRepository.save(server);

        if (ServerChangesStatus.valueOf(request.getStatus()).equals(ServerChangesStatus.Unsolved)){
            ChangesSolution solution = solutionRepository.save(ChangesSolution.builder()
                    .status(SolutionStatus.Unsolved)
                    .serverChanges(response)
                    .build());
            response.setSolution(solution);
            repository.save(response);
        }

        return mapper(response);
    }
    @Override
    public ServerChangesResponse getServerChangesDetail(String id){
        try {
            ServerChanges serverChanges = repository.getReferenceById(id);
            return mapper(serverChanges);
        } catch (Exception e){
            throw new RuntimeException("Id is wrong");
        }
    }

    @Override
    public ServerChangesResponse updateServerChanges(String id, UpdateChangesRequest request){
        try {
            ServerChanges serverChanges = repository.getReferenceById(id);

            if (!request.getTipePerbaikan().isEmpty()) serverChanges.setTipePerbaikan(request.getTipePerbaikan());
            if (!request.getDetailPerbaikan().isEmpty()) serverChanges.setDetailPerbaikan(request.getDetailPerbaikan());
            if (!request.getStatus().isEmpty()) serverChanges.setStatus(ServerChangesStatus.valueOf(request.getStatus()));

            ServerChanges response = repository.save(serverChanges);

            if (ServerChangesStatus.valueOf(request.getStatus()).equals(ServerChangesStatus.Unsolved) && response.getSolution() == null){
                ChangesSolution solution = solutionRepository.save(ChangesSolution.builder()
                        .status(SolutionStatus.Unsolved)
                        .serverChanges(response)
                        .build());
                response.setSolution(solution);
                repository.save(response);
            }

            return mapper(response);
        } catch (Exception e){
            throw new RuntimeException("Id is wrong");
        }
    }

    @Override
    public Boolean deleteServer(String id) throws RuntimeException, EntityNotFoundException {
        try {
            ServerChanges serverChanges = repository.getReferenceById(id);
            if (serverChanges.getStatus().equals(ServerChangesStatus.Draft)){
                repository.delete(serverChanges);
                return true;
            }

            if (serverChanges.getStatus().equals(ServerChangesStatus.Ditolak)){
                ChangesSolution solution = serverChanges.getSolution();
                serverChanges.setSolution(null);

                repository.save(serverChanges);

                solutionRepository.delete(solution);
                repository.delete(serverChanges);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ServerChangesResponse> getServerChangesByDivisi(String divisi) {
        List<ServerChanges> serverChanges = repository.findServerChangesByDivisi(divisi);
        return serverChanges.stream().map(this::mapper).collect(Collectors.toList());
    }

    @Override
    public List<ServerChangesResponse> getServerChangesByDivisiName(String divisiName) {
        List<ServerChanges> serverChanges = repository.findServerChangesByDivisiName(divisiName);
        return serverChanges.stream().map(this::mapper).collect(Collectors.toList());
    }

    @Override
    public List<FinishedChangesResponse> getAll() {
        List<ServerChanges> response = repository.findAll();
        return response.stream().map(this::finishedMapper).collect(Collectors.toList());
    }

    @Override
    public Boolean rejectServerChanges(String id) {
        ServerChanges serverChanges = repository.getReferenceById(id);
        if (serverChanges.getSolution().getStatus() != null && serverChanges.getSolution().getStatus().equals(SolutionStatus.Solved)) throw new RuntimeException("Perubahan tidak dapat ditolak");

        serverChanges.setStatus(ServerChangesStatus.Ditolak);
        repository.save(serverChanges);
        return true;
    }
    @Override
    public List<FinishedChangesResponse> getAllFinishByDivisiId(String id){
        List<ServerChanges> response = repository.findserverChangesFinishedByDivisiId(id);
        return response.stream().map(this::finishedMapper).collect(Collectors.toList());
    }
    @Override
    public List<FinishedChangesResponse> getAllSolvedSolution(){
        List<ServerChanges> response = repository.findFinishedChangesSolution();
        return response.stream().map(this::finishedMapper).collect(Collectors.toList());
    }
    @Override
    public FinishedChangesResponse assingDeveloper(String changesId, AssignUpdateDeveloperRequest request)throws EntityNotFoundException, RuntimeException{
        String[] devIds = request.getDeveloperIds().split(",");
        ServerChanges serverChanges = repository.getReferenceById(changesId);

        if (serverChanges.getSolution().getStatus()!= SolutionStatus.Solved) throw new RuntimeException("Solusi belum dibuat");

        if (request.getAssignStatus().equals(DeveloperAssignStatus.Draft.toString())){
            for (String id: devIds){
                Developer developer = developerRepository.getReferenceById(id);
                if (developer.getStatus().equals(DeveloperStatus.Unavailable)) throw new RuntimeException("Terdapat developer yang sedang tidak tersedia");
                serverChanges.getDevelopers().add(developer);
                developerRepository.save(developer);
            }
            serverChanges.setAssignStatus(DeveloperAssignStatus.Draft);
        } else {
            for (String id: devIds){
                Developer developer = developerRepository.getReferenceById(id);
                if (developer.getStatus().equals(DeveloperStatus.Unavailable)) throw new RuntimeException("Developer sedang tidak tersedia");
                developer.setStatus(DeveloperStatus.Unavailable);
                serverChanges.getDevelopers().add(developer);
                developerRepository.save(developer);
            }
            Server server = serverChanges.getServer();
            server.setStatus(Status.MAINTENANCE);
            serverChanges.setAssignStatus(DeveloperAssignStatus.Assigned);
        }

        return finishedMapper(repository.save(serverChanges));

    }

    public FinishedChangesResponse getResponseById(String changesId){
        Optional<ServerChanges> changesOpt = repository.findById(changesId);
        if (changesOpt.isEmpty()) throw new RuntimeException("Id not found");
        return finishedMapper(changesOpt.get());
    }

    public ChartResponse getLineGraphDailySummary(SummaryRequest request, UserModel user){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateStart = LocalDate.parse(request.getStart(), formatter);
        LocalDate dateEnd = LocalDate.parse(request.getEnd(), formatter);
        Long daysBetween = ChronoUnit.DAYS.between(dateStart, dateEnd);

        Long[] values = new Long[Math.toIntExact(daysBetween) + 1];
        String[] labels = new String[Math.toIntExact(daysBetween) + 1];

        for (int i = 0; i < labels.length; i++) {
            labels[i] = dateStart.plusDays(i).format(formatter);
        }

        List<LineChartView> data;

        if (user.getRole().equals(Role.Anggota)){
            String divisi = user.getDivisi().getNama();
            data = repository.lineChartSummaryDayByDivisi(request.getStart(), request.getEnd(), divisi);
        } else if (request.getDivision() != null && !request.getDivision().isEmpty()){
            data = repository.lineChartSummaryDayByDivisi(request.getStart(), request.getEnd(), request.getDivision());
        }   else {
            data = repository.lineChartSummaryDay(request.getStart(), request.getEnd());
        }

        for (int i = 0; i < data.size(); i++) {
            LocalDate dataDate = LocalDate.parse(data.get(i).getTime(), formatter);
            Long daysBetweenData = ChronoUnit.DAYS.between(dateStart, dataDate);
            values[Math.toIntExact(daysBetweenData)] = data.get(i).getTotalChanges();
        }
        return new ChartResponse(labels, values);
    }

    public ChartResponse getLineGraphMonthlySummary(SummaryRequest request, UserModel user){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateStart = LocalDate.parse(request.getStart(), formatter);
        LocalDate dateEnd = LocalDate.parse(request.getEnd(), formatter);
        Long daysBetween = ChronoUnit.MONTHS.between(dateStart.withDayOfMonth(1), dateEnd.withDayOfMonth(1));

        Long[] values = new Long[Math.toIntExact(daysBetween) + 1];
        String[] labels = new String[Math.toIntExact(daysBetween) + 1];

        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM");

        for (int i = 0; i < labels.length; i++) {
            labels[i] = dateStart.plusMonths(i).format(monthFormatter);
        }

        List<LineChartView> data;

        if (user.getRole().equals(Role.Anggota)){
            String divisi = user.getDivisi().getNama();
            data = repository.lineChartSummaryMonthByDivisi(request.getStart(), request.getEnd(), divisi);
        } else if (request.getDivision() != null && !request.getDivision().isEmpty()){
            data = repository.lineChartSummaryMonthByDivisi(request.getStart(), request.getEnd(), request.getDivision());
        }   else {
            data = repository.lineChartSummaryMonth(request.getStart(), request.getEnd());
        }

        for (int i = 0; i < data.size(); i++) {
            LocalDate dataDate = LocalDate.parse(data.get(i).getTime().substring(0, 10), formatter);
            Long daysBetweenData = ChronoUnit.MONTHS.between(dateStart.withDayOfMonth(1), dataDate.withDayOfMonth(1));
            values[Math.toIntExact(daysBetweenData)] = data.get(i).getTotalChanges();
        }
        return new ChartResponse(labels, values);
    }

    public ChartResponse getPieChartSummary(SummaryRequest request, UserModel user){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateStart = LocalDate.parse(request.getStart(), formatter);
        LocalDate dateEnd = LocalDate.parse(request.getEnd(), formatter);

        List<PieChartView> data;
        if (user.getRole().equals(Role.Anggota)){
            String divisi = user.getDivisi().getNama();
            data = repository.pieChartSummaryByDivisi(request.getStart(), request.getEnd(), divisi);
        } else if (request.getDivision() != null && !request.getDivision().isEmpty()){
            data = repository.pieChartSummaryByDivisi(request.getStart(), request.getEnd(), request.getDivision());
        }   else {
            data = repository.pieChartSummary(request.getStart(), request.getEnd());
        }

        Long[] values = new Long[data.size()];
        String[] labels = new String[data.size()];

        for (int i = 0; i < data.size(); i++) {
            labels[i] = data.get(i).getTipePerbaikan();
            values[i] = data.get(i).getJumlah();
        }

        return new ChartResponse(labels, values);
    }
    @Override
    public FinishedChangesResponse updateDeveloper(String changesId, AssignUpdateDeveloperRequest request) throws RuntimeException{
        ServerChanges serverChanges = repository.getReferenceById(changesId);
        if (serverChanges.getDevelopers().isEmpty()) throw new RuntimeException("Belum ter-assign developer");
        if (serverChanges.getAssignStatus() != null && serverChanges.getAssignStatus().equals(DeveloperAssignStatus.Assigned)) throw new RuntimeException("Tidak dapat meng-assign developer");

        String[] devIds = request.getDeveloperIds().split(",");

        if (request.getAssignStatus().equals(DeveloperAssignStatus.Draft.toString())){
            serverChanges.getDevelopers().clear();
            for (String id: devIds){
                Developer developerNew = developerRepository.getReferenceById(id);
                if (developerNew.getStatus().equals(DeveloperStatus.Unavailable)) throw new RuntimeException("Developer tidak tersedia");
                serverChanges.getDevelopers().add(developerNew);
            }
            serverChanges.setAssignStatus(DeveloperAssignStatus.Draft);
        } else {
            serverChanges.getDevelopers().clear();
            for (String id: devIds){
                Developer developerNew = developerRepository.getReferenceById(id);
                if (developerNew.getStatus().equals(DeveloperStatus.Unavailable)) throw new RuntimeException("Developer tidak tersedia");
                developerNew.setStatus(DeveloperStatus.Unavailable);
                developerRepository.save(developerNew);
                serverChanges.getDevelopers().add(developerNew);
            }
            serverChanges.setAssignStatus(DeveloperAssignStatus.Assigned);
        }
        return finishedMapper(repository.save(serverChanges));
    }

    public FinishedChangesResponse finishChanges(String changesId) throws RuntimeException{
        ServerChanges serverChanges = repository.getReferenceById(changesId);
        if (serverChanges.getStatus().equals(ServerChangesStatus.Unsolved) && serverChanges.getSolution() != null && serverChanges.getSolution().getStatus().equals(SolutionStatus.Solved) && !serverChanges.getDevelopers().isEmpty()){
            for (Developer dev: serverChanges.getDevelopers()){
                dev.setStatus(DeveloperStatus.Available);
                developerRepository.save(dev);
            }
            serverChanges.setStatus(ServerChangesStatus.Solved);
            return finishedMapper(repository.save(serverChanges));
        }
        throw new RuntimeException("Terdapat kesalahan");

    }

    @Override
    public FinishedChangesResponse getFinishById(String changesId) {
        ServerChanges changes = repository.getReferenceById(changesId);
        return finishedMapper(changes);
    }

    private ServerChangesResponse mapper(ServerChanges serverChanges){
        ServerChangesResponse response = ServerChangesResponse.builder()
                .id(serverChanges.getId())
                .serverId(serverChanges.getServer().getId())
                .serverName(serverChanges.getServer().getNama())
                .tipePerbaikan(serverChanges.getTipePerbaikan())
                .detailPerbaikan(serverChanges.getDetailPerbaikan())
                .tanggalDibuat(serverChanges.getTanggalDibuat().toString())
                .status(serverChanges.getStatus().toString())
                .username(serverChanges.getAnggota().getUsername())
                .divisi(serverChanges.getServer().getDivisi().getNama())
                .build();

        if (serverChanges.getTanggalSelesai() != null) response.setTanggalSelesai(serverChanges.getTanggalSelesai().toString());
        if (serverChanges.getSolution() != null) {
            response.setSolutionId(serverChanges.getSolution().getId());
            if (serverChanges.getStatus().toString().equals("Ditolak")) {
                response.setSolutionStatus("Ditolak");
            } else if (serverChanges.getStatus().toString().equals("Draft")) {
                response.setSolutionStatus("Unsolved");
            } else {
                response.setSolutionStatus(serverChanges.getSolution().getStatus().toString());
            }
        }
        return response;
    }

    private FinishedChangesResponse finishedMapper(ServerChanges serverChanges){
        FinishedChangesResponse response = FinishedChangesResponse.builder()
                .id(serverChanges.getId())
                .serverId(serverChanges.getServer().getId())
                .serverName(serverChanges.getServer().getNama())
                .tipePerbaikan(serverChanges.getTipePerbaikan())
                .detailPerbaikan(serverChanges.getDetailPerbaikan())
                .tanggalDibuat(String.valueOf(serverChanges.getTanggalDibuat()))
                .status(serverChanges.getStatus().toString())
                .divisi(serverChanges.getServer().getDivisi().getNama())
                .build();

        if (!serverChanges.getDevelopers().isEmpty()){
            List<DeveloperResponse> devResponses = serverChanges.getDevelopers().stream().map(this::devMapper).collect(Collectors.toList());
            response.setDevelopers(devResponses);
        }

        if (serverChanges.getTanggalSelesai() != null){
            response.setTanggalSelesai(String.valueOf(serverChanges.getTanggalSelesai()));
        }

        if (serverChanges.getAssignStatus() != null){
            response.setAssignStatus(String.valueOf(serverChanges.getAssignStatus()));
        }

        if (serverChanges.getSolution() != null){
            response.setSolution(serverChanges.getSolution().getId());
            response.setSolutionStatus(serverChanges.getSolution().getStatus().toString());
            if (serverChanges.getSolution().getSolution() != null && serverChanges.getSolution().getSolution().isEmpty()) {
                response.setSolution(serverChanges.getSolution().getSolution());
            } else {
                response.setSolution("");
            }
        }

        return response;
    }

    private DeveloperResponse devMapper(Developer dev){
        return new DeveloperResponse(dev.getId(), dev.getNama(), dev.getKeahlian(), dev.getStatus().toString());
    }
}
