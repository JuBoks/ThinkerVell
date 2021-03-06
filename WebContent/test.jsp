<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<p>Fun level <span id="target" style="font-size: 10px; color: red;">50 %</span>.</p>

<!--script src="http://code.jquery.com/jquery-2.0.3.min.js"></script-->
<!--script src="http://code.jquery.com/jquery-2.0.2.min.js"></script-->
<!--script src="http://code.jquery.com/jquery-2.0.1.min.js"></script-->
<!--script src="http://code.jquery.com/jquery-2.0.0.min.js"></script-->
<!--script src="http://code.jquery.com/jquery-1.10.2.min.js"></script-->
<!--script src="http://code.jquery.com/jquery-1.10.1.min.js"></script-->
<!--script src="http://code.jquery.com/jquery-1.10.0.min.js"></script-->
<!--script src="http://code.jquery.com/jquery-1.9.1.min.js"></script-->
<!--script src="http://code.jquery.com/jquery-1.9.0.min.js"></script-->
<!--script src="http://code.jquery.com/jquery-1.8.3.min.js"></script-->
<!--script src="http://code.jquery.com/jquery-1.8.2.min.js"></script-->
<!--script src="http://code.jquery.com/jquery-1.8.1.min.js"></script-->
<!--script src="http://code.jquery.com/jquery-1.8.0.min.js"></script-->
<!-- <script src="http://code.jquery.com/jquery-1.7.0.min.js"></script> -->
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>

<script src="jquery.color.min.js"></script>
<script src="../jquery.animateNumber.min.js"></script>

<script>
  var decimal_places = 1;
  var decimal_factor = decimal_places === 0 ? 1 : decimal_places * 10;
  $('#target').animateNumber(
    {
      number: 100 * decimal_factor,
      color: 'green',
      'font-size': '30px',
      numberStep: function(now, tween) {
        var floored_number = Math.floor(now) / decimal_factor,
            target = $(tween.elem);
        if (decimal_places > 0) {
          floored_number = floored_number.toFixed(decimal_places);
        }
        target.text(floored_number + ' %');
      }
    },
    {
      easing: 'swing',
      duration: 15000
    }
  )
</script>