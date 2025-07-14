package top.hendrixshen.tweakmyclient.impl.config;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import top.hendrixshen.magiclib.api.malilib.config.option.EnumOptionEntry;
import top.hendrixshen.tweakmyclient.SharedConstants;

import java.util.List;
import java.util.Set;

public abstract class EitherUsageRestriction<ORIGINAL_TYPE, TYPE> {
    @Getter
    @Setter
    protected EitherListType listType;
    protected final Set<TYPE> blackList;
    protected final Set<TYPE> whiteList;

    public EitherUsageRestriction() {
        this.listType = EitherListType.BLACKLIST;
        this.blackList = Sets.newHashSet();
        this.whiteList = Sets.newHashSet();
    }

    public void setListContents(List<ORIGINAL_TYPE> blacklist, List<ORIGINAL_TYPE> whitelist) {
        this.setValuesForList(EitherListType.BLACKLIST, blacklist);
        this.setValuesForList(EitherListType.WHITELIST, whitelist);
    }

    public Set<TYPE> getListForType(EitherListType type) {
        return type == EitherListType.WHITELIST ? this.whiteList : this.blackList;
    }

    public void setValuesForList(EitherListType type, List<ORIGINAL_TYPE> config) {
        Set<TYPE> set = this.getListForType(type);
        set.clear();
        this.setValuesForList(set, config);
    }

    protected void setValuesForList(Set<TYPE> set, List<ORIGINAL_TYPE> list) {
        list.forEach(item -> set.add(this.convert(item)));
    }

    protected abstract TYPE convert(ORIGINAL_TYPE type);

    public boolean isAllowed(TYPE value) {
        return this.listType == EitherListType.WHITELIST ? this.whiteList.contains(value) : !this.blackList.contains(value);
    }

    public enum EitherListType implements EnumOptionEntry {
        BLACKLIST,
        WHITELIST;

        public static final EitherListType DEFAULT = EitherListType.BLACKLIST;

        @Override
        public EnumOptionEntry[] getAllValues() {
            return EitherListType.values();
        }

        @Override
        public EnumOptionEntry getDefault() {
            return EitherListType.DEFAULT;
        }

        @Override
        public String getTranslationPrefix() {
            return SharedConstants.getModIdentifier().concat(".config.gui.either_list_type");
        }
    }
}
