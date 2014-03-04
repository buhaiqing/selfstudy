// Author: Bu Hai-Qing (hai-qing.bu@hp.com)
// Description: use to check if one review has at least one owner to ship it
import static groovyx.gpars.GParsPool.withPool

class ReviewLinkStatusChecker
{
    def link

    public void check()
    {
        def html = link.toURL().text
        if (!html.contains('<div class="shipit">'))
        {
            println "No one ship the review ticket at ${link}"
        }
        //        else
        //        {
        //            println("ok")
        //        }
    }
}

withPool 16, {
    def test_links = [
            'http://reviewboard.chn.hp.com/reviews/r/5453/',
            'http://reviewboard.chn.hp.com/reviews/r/4783/'
    ]
    test_links.eachParallel {
        def test_link = it
        def test_obj = new ReviewLinkStatusChecker(link: test_link)
        test_obj.check()
    }
}