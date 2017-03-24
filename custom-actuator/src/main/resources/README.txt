Configuration for using RequestMapping
Using spring.factories and @ManagementContextConfiguration for creating
autoconfig and starter with your own actuator url

---------------------------------------------------------------
     public class ExampleEndpoint extends EndpointMvcAdapter *//* new AbstractNamedMvcEndpoint super("logfile", "/logfile", true);*//* {

        private final ListEndpoints delegate;

        public ExampleEndpoint(ListEndpoints delegate) {
            super(delegate);
            this.delegate = delegate;
        }

    @RequestMapping(value = "/customEndpoint/filter", method = RequestMethod.GET)
    @ResponseBody
    public Set<Endpoint> filter(@RequestParam(required = false) Boolean enabled,
                                @RequestParam(required = false) Boolean sensitive) {
        return
    }*/
