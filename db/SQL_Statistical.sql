create Proc sp_getTotalPricePerMonth(
	@month varchar(2),
	@year varchar(4)
)
as begin
	Declare @result varchar(20)
	SET @result =(Select
						SUM(order_details.price * order_details.quantity) as 'totalPrice'
					From 
						orders
						inner join order_details on orders.id = order_details.orderId
					WHERE 
						MONTH(orders.createdDate) = @month
						AND YEAR(orders.createdDate) = @year)
	IF @result IS NULL begin SET @result = '0' END
	Select @result
end