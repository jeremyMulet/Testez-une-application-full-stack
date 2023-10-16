import { run as runUser } from "./user.cy"
import { run as runLogin } from "./login.cy";
import { run as runRegister } from "./register.cy";
import { run as runAdmin } from "./admin.cy";

runLogin();
runRegister();
runAdmin();
runUser();
