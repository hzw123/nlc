$(function() {
    $('.bs-example').each(function() {
        var source = $('<div></div>').text($(this).html()).html(),
            sources = source.split('\n'),
            codes = [],
            spaces = 0;

        $.each(sources, function(i, text) {
            if (!$.trim(text)) {
                return;
            }
            if (!spaces) {
                spaces = text.match(/(^\s+)/)[1].length;
            }
            codes.push(text.substring(spaces));
        });
        $(this).next().find('code').html(codes.join('\n'));
    });
    $("#state_box,#level_box").css({"padding-left":"10px",width:'130px'});
	$("#state_box option,#level_box option").css({"padding-left":"10px"});
});