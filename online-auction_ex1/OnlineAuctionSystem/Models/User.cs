namespace OnlineAuctionSystem.Models
{
    public class User
    {
        public int UserId { get; set; }
        public string? Username { get; set; }
        public string? Email { get; set; } // Make Email nullable
    }
}
