$(function() {
    $(".tile").draggable({
        revert: "invalid",
        start: function(event, ui) {
            $(this).css("z-index", 100);
        },
        stop: function(event, ui) {
            $(this).css("z-index", 10);
        }
    });

    $(".grid div").droppable({
        accept: ".tile",
        hoverClass: "hovered",
        drop: function(event, ui) {
            var tile = ui.draggable;
            $(this).append(tile);
            $(tile).css({
                top: "0px",
                left: "0px"
            });
        }
    });
});
