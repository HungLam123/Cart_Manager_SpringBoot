use mockproject_t4_2022
go
select Top 8 name, SUM(hdct.quantity) as SoLuongDaBan
from products sp, order_details hdct
where sp.id = hdct.productId
group by name
order by SoLuongDaBan DESC
go

CREATE Proc Top5SPBanNhieuNhat
AS
BEGIN
	SELECT 
		Top 8 name, SUM(hdct.quantity) as SoLuongDaBan
	FROM 
		products sp, order_details hdct
	where 
		sp.id = hdct.productId and sp.isDeleted = 0 and sp.quantity > 0
	group by 
		name
	order by SoLuongDaBan DESC
END;

