namespace OnlineAuctionSystem.Models
{
    public class Item
    {
        public int ItemId { get; set; }
        public string? Name { get; set; }
        public string? Description { get; set; }
        public decimal StartingBid { get; set; }
        public decimal CurrentBid { get; set; }
        public int UserId { get; set; }
        public User? User { get; set; }
    }
}
