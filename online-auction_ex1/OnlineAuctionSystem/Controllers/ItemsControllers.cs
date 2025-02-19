using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using OnlineAuctionSystem.Data;
using OnlineAuctionSystem.Models;
using System.Linq;
using System.Threading.Tasks;

namespace OnlineAuctionSystem.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ItemsController : ControllerBase
    {
        private readonly AuctionContext _context;

        public ItemsController(AuctionContext context)
        {
            _context = context;
        }

        [HttpPost]
        public async Task<ActionResult<Item>> AddItem(Item item)
        {
            _context.Items.Add(item);
            await _context.SaveChangesAsync();
            return CreatedAtAction(nameof(GetItem), new { id = item.ItemId }, item);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Item>> GetItem(int id)
        {
            var item = await _context.Items.Include(i => i.User).FirstOrDefaultAsync(i => i.ItemId == id);
            if (item == null)
            {
                return NotFound();
            }
            return item;
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> EditItem(int id, Item item)
        {
            if (id != item.ItemId)
            {
                return BadRequest();
            }

            _context.Entry(item).State = EntityState.Modified;
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!await ItemExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteItem(int id)
        {
            var item = await _context.Items.FindAsync(id);
            if (item == null)
            {
                return NotFound();
            }

            _context.Items.Remove(item);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        [HttpGet("download/{id}")]
        public async Task<IActionResult> DownloadItemInfo(int id)
        {
            var item = await _context.Items.Include(i => i.User).FirstOrDefaultAsync(i => i.ItemId == id);
            if (item == null)
            {
                return NotFound();
            }

            var itemInfo = new
            {
                item.ItemId,
                item.Name,
                item.Description,
                item.StartingBid,
                item.CurrentBid,
                Owner = item.User != null ? item.User.Username : "Unknown"
            };

            return Ok(itemInfo);
        }

        private async Task<bool> ItemExists(int id)
        {
            return await _context.Items.AnyAsync(e => e.ItemId == id);
        }
    }
}
