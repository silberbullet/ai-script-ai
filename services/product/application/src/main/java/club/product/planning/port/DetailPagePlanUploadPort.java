package club.product.planning.port;

import club.product.planning.detailpage.domain.DetailPagePlan;

public interface DetailPagePlanUploadPort {
    void uploadDetailPageImage(DetailPagePlan detailPagePlan);
}
