<!DOCTYPE html>
<html language="en-us">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Show And Hidden Demo</title>
        <link rel="stylesheet" href="css/style.css" type="text/css" >
        <script type="text/javascript"
            charset="utf-8"
            src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js">
        </script>
        <script type="text/javascript"
            charset="utf-8"
            src="js/application.js">
         
 
        //支持IE6,IE7和IE8的html5结构元素
        document.createElement("header");
        document.createElement("nav");
        document.createElement("section");
        document.createElement("article");
        document.createElement("aside");
        document.createElement("footer");
 
 
        $(function(){
             
            //首先要将#service,contact,about部分隐藏
            $("#service,#about,#contact").hide().addClass("hidden");
             
            //将#welcome内容设为显示
            $("#welcom").addClass("visible");
     
            //捕捉导航里的所有单击操作
            $("nav ul").click(function(event){
                 
                target=$(event.target);
                //判断单击的是什么元素,如果是单击是一个超链接,则判断它相应的section部分是否是隐藏状态
                if(target.is("a")){
                    event.preventDefault();
                    //如果判断出它相应的部分是隐藏状态,则应该将其它部分设为隐藏,将该部分设为显示
                    if($(target.attr("href")).hasClass("hidden")){
                        $(".visible").removeClass("visible")
                                 .addClass("hidden")
                                     .hide();
                        $(target.attr("href")).removeClass("hidden")
                                          .addClass("visible")      
                                      .show();
                    };
                };
            });
        });
        </script>
    </head>
    <body>
        <!--SITE HRADER-->
        <header id="page_header">
            <h1>Demo Test</h1>
            <nav>
                <ul>
                    <li><a href="#Welcome">Welcome</a></li>
                    <li><a href="#Service">Service</a></li>
                    <li><a href="#Contact">Contact</a></li>
                    <li><a href="#About">About</a></li>
                </ul>
            </nav>
        </header><!--site header-->
        <section id="content" role="document" aria-live="assertive" aria-automic="true">
            <section id="welcome">
                <header>
                    <h1>Welcome</h1>
                </header>
                <p>The Welcome section</p>
            </section>
            <section id="service">
                <header>
                    <h1>Service</h1>
                </header>
                <p>The service section</p>
            </section>
            <section id="Contact">
                <header>
                    <h1>Contact</h1>
                </header>
                <p>The Contact section</p>
            </section>    
            <section id="About">
                <header>
                    <h1>About</h1>
                </header>
                <p>The About section</p>
            </section>
        </section>
    </body>
</html>