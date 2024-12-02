package propensi.project.Assettrackr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import propensi.project.Assettrackr.model.ServerChanges;
import propensi.project.Assettrackr.model.projection.LineChartView;
import propensi.project.Assettrackr.model.projection.PieChartView;

import java.util.List;

public interface ServerChangesRepository extends JpaRepository<ServerChanges, String> {
    @Query(value = "SELECT * FROM server_changes sc, server se WHERE sc.server_id = se.id AND se.divisi_id = :divisiId", nativeQuery = true)
    List<ServerChanges> findServerChangesByDivisi(String divisiId);

    @Query(value = "SELECT * FROM server_changes sc, server se, divisi di WHERE sc.server_id = se.id AND se.divisi_id = di.id AND di.nama= :divisiName", nativeQuery = true)
    List<ServerChanges> findServerChangesByDivisiName(String divisiName);

    @Query(value = "SELECT * FROM server_changes sc, server se WHERE sc.server_id = se.id AND se.divisi_id = :divisiId AND sc.status = 'Solved'", nativeQuery = true)
    List<ServerChanges> findserverChangesFinishedByDivisiId(String divisiId);

    @Query(value = "SELECT * FROM server_changes sc, changes_solution cs WHERE sc.id = cs.server_changes_id AND cs.status = 'Solved'", nativeQuery = true)
    List<ServerChanges> findFinishedChangesSolution();

    @Query(value = "SELECT sc.tanggal_dibuat as Time, COUNT(*) AS TotalChanges FROM server_changes sc WHERE sc.tanggal_dibuat BETWEEN CAST(:start AS DATE) AND CAST(:end AS DATE) GROUP BY sc.tanggal_dibuat order by Time", nativeQuery = true)
    List<LineChartView> lineChartSummaryDay(String start, String end);

    @Query(value = "SELECT DATE_TRUNC('month', tanggal_dibuat) as Time, COUNT(*) AS TotalChanges FROM server_changes WHERE tanggal_dibuat BETWEEN CAST(:start AS DATE) AND CAST(:end AS DATE) GROUP BY DATE_TRUNC('month', tanggal_dibuat) ORDER BY Time", nativeQuery = true)
    List<LineChartView> lineChartSummaryMonth(String start, String end);

    @Query(value = "SELECT sc.tanggal_dibuat as Time, COUNT(*) AS TotalChanges FROM server_changes sc, server se WHERE sc.server_id = se.id AND se.divisi_id = :divisiId AND sc.tanggal_dibuat BETWEEN CAST(:start AS DATE) AND CAST(:end AS DATE) GROUP BY sc.tanggal_dibuat order by Time", nativeQuery = true)
    List<LineChartView> lineChartSummaryDayByDivisi(String start, String end, String divisiId);

    @Query(value = "SELECT DATE_TRUNC('month', sc.tanggal_dibuat) as Time, COUNT(*) AS TotalChanges FROM server_changes sc, server se WHERE sc.server_id = se.id AND se.divisi_id = :divisiId AND sc.tanggal_dibuat BETWEEN CAST(:start AS DATE) AND CAST(:end AS DATE) GROUP BY DATE_TRUNC('month', sc.tanggal_dibuat) ORDER BY Time", nativeQuery = true)
    List<LineChartView> lineChartSummaryMonthByDivisi(String start, String end, String divisiId);

    @Query(value = "SELECT sc.tipe_perbaikan as TipePerbaikan, COUNT(*) as Jumlah FROM server_changes sc WHERE sc.tanggal_dibuat BETWEEN CAST(:start AS DATE) AND CAST(:end AS DATE) GROUP BY sc.tipe_perbaikan", nativeQuery = true)
    List<PieChartView> pieChartSummary(String start, String end);
}
