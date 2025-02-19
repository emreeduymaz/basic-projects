using Microsoft.EntityFrameworkCore;
using OnlineAuctionSystem.Models;

namespace OnlineAuctionSystem.Data
{
    public class AuctionContext : DbContext
    {
        public AuctionContext(DbContextOptions<AuctionContext> options) : base(options) { }

        public DbSet<User> Users { get; set; }
        public DbSet<Item> Items { get; set; }
    }
}
