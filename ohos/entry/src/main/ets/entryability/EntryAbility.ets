/*
 * KaMPKit OHOS 最小可运行：仅加载 BreedListPage，不依赖 Kuikly/Napi。
 */

import { AbilityConstant, UIAbility, Want } from '@kit.AbilityKit';
import { hilog } from '@kit.PerformanceAnalysisKit';
import { window } from '@kit.ArkUI';

export default class EntryAbility extends UIAbility {
  onCreate(want: Want, launchParam: AbilityConstant.LaunchParam): void {
    hilog.info(0x0000, 'KaMPKit', '%{public}s', 'Ability onCreate');
  }

  onDestroy(): void {
    hilog.info(0x0000, 'KaMPKit', '%{public}s', 'Ability onDestroy');
  }

  onWindowStageCreate(windowStage: window.WindowStage): void {
    hilog.info(0x0000, 'KaMPKit', '%{public}s', 'Ability onWindowStageCreate');
    windowStage.loadContent('pages/BreedListPage', (err) => {
      if (err.code) {
        hilog.error(0x0000, 'KaMPKit', 'Failed to load content. Cause: %{public}s', JSON.stringify(err) ?? '');
        return;
      }
      hilog.info(0x0000, 'KaMPKit', 'Succeeded in loading BreedListPage.');
    });
    const mainWindow = windowStage.getMainWindowSync();
    mainWindow.setWindowLayoutFullScreen(true);
  }

  onWindowStageDestroy(): void {
    hilog.info(0x0000, 'KaMPKit', '%{public}s', 'Ability onWindowStageDestroy');
  }

  onForeground(): void {
    hilog.info(0x0000, 'KaMPKit', '%{public}s', 'Ability onForeground');
  }

  onBackground(): void {
    hilog.info(0x0000, 'KaMPKit', '%{public}s', 'Ability onBackground');
  }
}
