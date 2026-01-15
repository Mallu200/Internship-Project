$files = @(
 'd:\X-Workz_Project\prodex\src\main\webapp\resources\css\theme.css',
 'd:\X-Workz_Project\prodex\src\main\webapp\resources\js\common.js',
 'd:\X-Workz_Project\prodex\src\main\webapp\resources\js\adminDashboard.js',
 'd:\X-Workz_Project\prodex\src\main\webapp\WEB-INF\views\memberDashboard.jsp',
 'd:\X-Workz_Project\prodex\src\main\webapp\WEB-INF\views\addCustomer.jsp',
 'd:\X-Workz_Project\prodex\src\main\webapp\WEB-INF\views\editMember.jsp',
 'd:\X-Workz_Project\prodex\src\main\webapp\WEB-INF\views\editProfile.jsp',
 'd:\X-Workz_Project\prodex\src\main\webapp\WEB-INF\views\editAdminProfile.jsp',
 'd:\X-Workz_Project\prodex\src\main\webapp\WEB-INF\views\administratorPurchaseApproval.jsp',
 'd:\X-Workz_Project\prodex\src\main\webapp\WEB-INF\views\administratorDashboard.jsp',
 'd:\X-Workz_Project\prodex\src\main\webapp\WEB-INF\views\purchaseProduct.jsp',
 'd:\X-Workz_Project\prodex\src\main\webapp\resources\js\viewPendingRequests.js',
 'd:\X-Workz_Project\prodex\src\main\webapp\WEB-INF\views\viewPendingRequests.jsp',
 'd:\X-Workz_Project\prodex\src\main\webapp\WEB-INF\views\viewMembers.jsp',
 'd:\X-Workz_Project\prodex\src\main\webapp\WEB-INF\views\viewCustomers.jsp',
 'd:\X-Workz_Project\prodex\src\main\webapp\WEB-INF\views\viewCustomerDetails.jsp'
)

foreach ($f in $files) {
    $orig = $f + '.orig'
    if (Test-Path $orig) {
        try {
            Copy-Item -Path $orig -Destination $f -Force
            Write-Host "RESTORED: $f"
        } catch {
            Write-Host "ERROR copying $orig -> $f : $_"
        }
    } else {
        Write-Host "NO BACKUP: $orig not found"
    }
}
