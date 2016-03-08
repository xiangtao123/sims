/**
 * Created by young on 14-10-10.
 */


(function ($) {
    $.fn.iframe = function (url, options) {
        var imf = "<iframe style='width: 100%;height: 100%;border:none' src='" + url + "'></iframe>";
        this.html(imf);
    };
})(jQuery);