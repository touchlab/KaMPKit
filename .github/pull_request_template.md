<!--- [Issue-XYZ] Add issue number and title to Title above -->

<!-- Add issue link -->
Issue: https://github.com/touchlab/KaMPKit/issues/[issue number]

## Summary
<!--- Copy summary from issue link or write a shortened description of it -->

## Fix
<!-- What did you do to fix the issue? -->

## Testing
<!-- Remove any lines that were not performed -->
- `./gradlew :app:build`
- `./gradlew :shared:build`
- `xcodebuild -workspace ios/KaMPKitiOS.xcworkspace -scheme KaMPKitiOS 
    -sdk iphoneos -configuration Debug build -destination name="iPhone 8"`
- manual testing

<!-- If you made changes to the UI, please show us what it looks like now. -->
### **Screenshot / Video of App working with the Changes**
<img width="250" alt="fix in action" src="https://media.makeameme.org/created/yes-it-works.jpg">